package cn.htzb.easybuy.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.htzb.easybuy.biz.OrderService;
import cn.htzb.easybuy.biz.impl.OrderServiceImpl;
import cn.htzb.easybuy.entity.Order;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.Product;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/manage/Order"},name = "Order")
public class OrderServlet extends HttpServlet {
	private OrderService orderService;

	public void init() throws ServletException {
		orderService = new OrderServiceImpl();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String actionIndicator = req.getParameter("action");
		ActionResult result = new ActionResult("error");
		Validator validator = new Validator(Validator.toSingleParameters(req));
		
		if (actionIndicator == null){
			actionIndicator = "list";
		}
		if ("read".endsWith(actionIndicator)) {//显示详情
			result = read(req, validator);
		} else if ("list".endsWith(actionIndicator)) {//显示所有订单
			result = list(req, validator);
		} else if ("delete".endsWith(actionIndicator)) {//删除有订单
			result = delete(req, validator);
		} else if ("save".endsWith(actionIndicator)) {//保存订单
			boolean isEdit = true;
			String editIndicator = req.getParameter("entityId");
			if (Validator.isEmpty(editIndicator))
				isEdit = false;
			result = save(req, validator, isEdit);
		}
		if (!validator.hasErrors() && result.isRedirect()) {
			resp.sendRedirect(result.getViewName());
		} else {
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher(result.getViewName()).forward(req, resp);
		}
	}

	public ActionResult read(HttpServletRequest request, Validator validator) {
		Order order = orderService.findById(request.getParameter("entityId"));
		pupulateRequest(request, order);
		return new ActionResult("order-modify.jsp");
	}

	public ActionResult list(HttpServletRequest request, Validator validator) {
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		String orderIdStr = request.getParameter("entityId");
		String userNameStr = request.getParameter("userName");
		String condition = "";
		if (!Validator.isEmpty(orderIdStr))
			condition = condition + " EASYBUY_ORDER.eo_id=" + orderIdStr;
		if (!Validator.isEmpty(userNameStr)) {
			if (condition.length() > 0)
				condition = condition + " and ";
			condition = condition + "eo_user_name like '%" + userNameStr + "%'";
		}
		//如果是普通用户登录
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("loginUser");	
		if(!user.isAdministrator()){
			if (condition.length() > 0)
				condition = condition + " and  EASYBUY_ORDER.EO_USER_ID ='" + user.getUserId()+"'";
			else
				condition = condition + "  EASYBUY_ORDER.EO_USER_ID ='" + user.getUserId()+"'";
		}
		long rowCount = orderService.getOrderRowCount(condition);
		String rowPerPage = request.getParameter("rowPerPage");
		Pager pager = new Pager(rowCount, pageNo);
		if (!Validator.isEmpty(rowPerPage))
			pager = new Pager(rowCount, Integer.parseInt(rowPerPage), pageNo);
		//获取订单及其详情
		Map <Order,ArrayList<Product>> map = orderService.getOrders(condition, pager);
		
		request.setAttribute("orders", map);
		request.setAttribute("pager", pager);
		request.setAttribute("pageNo", pageNo);
		return new ActionResult("order.jsp");
	}

	public ActionResult delete(HttpServletRequest request, Validator validator) {
		orderService.delete(request.getParameter("entityId"));
		return new ActionResult("Order", true);
	}

	public ActionResult save(HttpServletRequest request, Validator validator,
			boolean isEdit) {
		saveToDatabase(request, validator, isEdit);
		return new ActionResult("Order", true);
	}

	private void saveToDatabase(HttpServletRequest request,
			Validator validator, boolean isEdit) {
		if (!validator.hasErrors()) {
			Order order;
			if (!isEdit) {
				order = new Order();
				populateEntity(request, order);
				orderService.saveOrder(order);
			} else {
				order = orderService.findById(request.getParameter("entityId"));
				populateEntity(request, order);
				orderService.updateOrder(order);
			}
		}
	}

	private void populateEntity(HttpServletRequest request, Order order) {
		order.setStatus(Integer.parseInt(request.getParameter("status")));
	}

	private void pupulateRequest(HttpServletRequest request, Order order) {
		request.setAttribute("entityId", Long.toString(order.getId()));
		request.setAttribute("userId", order.getUserId());
		request.setAttribute("userName", order.getUserName());
		request.setAttribute("userAddress", order.getUserAddress());
		request.setAttribute("cost", Float.toString(order.getCost()));
		request.setAttribute("createTime", Validator.dateToString(order
				.getCreateTime()));
		request.setAttribute("status", Integer.toString(order.getStatus()));
	}
}
