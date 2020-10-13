package cn.htzb.easybuy.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.htzb.easybuy.biz.FacilityService;
import cn.htzb.easybuy.biz.ProductService;
import cn.htzb.easybuy.biz.impl.FacilityServiceImpl;
import cn.htzb.easybuy.biz.impl.ProductServiceImpl;
import cn.htzb.easybuy.entity.Comment;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.ProductCategory;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/manage/GuestBook","/GuestBook"},name = "GuestBook")
public class CommentServlet extends HttpServlet {
	private FacilityService facilityService;
	private ProductService productService;
	private boolean flag=false;
	public void init() throws ServletException {
		this.facilityService = new FacilityServiceImpl();
		this.productService = new ProductServiceImpl();
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
		if (actionIndicator == null)
			actionIndicator = "list";
		if ("read".endsWith(actionIndicator)) {//查询详情
			result = read(req, validator);
		} else if ("list".endsWith(actionIndicator)) {//显示所有
			User user = getUserFromSession(req);
			if(user==null){//如果尚未登录则退出
				Validator v = new Validator(Validator
						.toSingleParameters(req));
				v.addError("userId", "您尚未登录");
				req.setAttribute("errors", v.getErrors());
				req.getRequestDispatcher("login.jsp").forward(req, resp);
				return;
			}
			result = list(req, validator);//查找所有的留言 
		} else if ("delete".endsWith(actionIndicator)) {//删除某条
			result = delete(req, validator);
		} else if ("save".endsWith(actionIndicator)) {//保存
			boolean isEdit = true;
			String editIndicator = req.getParameter("entityId");
			if (Validator.isEmpty(editIndicator))
				isEdit = false;
			result = save(req, validator,isEdit);		
		}

		if (!validator.hasErrors() && result.isRedirect()) {
			resp.sendRedirect(result.getViewName());
			return;
		} else {
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher(result.getViewName()).forward(req, resp);
			return;
		}		
	}
	private User getUserFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute("loginUser");
	}
	public ActionResult read(HttpServletRequest request, Validator validator) {
		Comment comment = facilityService.findCommentById(request
				.getParameter("entityId"));
		pupulateRequest(request, comment);
		return new ActionResult("guestbook-modify.jsp");
	}

	public ActionResult save(HttpServletRequest request, Validator validator,
			boolean isEdit) {
		flag=checkInputErrors(request);
		saveToDatabase(request, isEdit);
		return new ActionResult("GuestBook", true);
	}

	public ActionResult delete(HttpServletRequest request, Validator validator) {
		facilityService.deleteComment(request.getParameter("entityId"));
		return new ActionResult("GuestBook", true);
	}

	public ActionResult list(HttpServletRequest request, Validator validator) {			
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		long rowCount = facilityService.getCommentRowCount();
		Pager pager = new Pager(rowCount, pageNo);
		String doUser = request.getParameter("do");//前台留言与后台留言排序不一致
		List<Comment> comments=null;
		if (doUser == null){
			comments = facilityService.getComments(pager,false);//未回复，和创建最早的留言在最前
		}else{
			comments = facilityService.getComments(pager,true);//建最晚的留言在最前
		}		
		List<ProductCategory> categories = productService
				.getProductCategories(null);
		request.setAttribute("categories", categories);
		request.setAttribute("comments", comments);
		request.setAttribute("pager", pager);
		request.setAttribute("pageNo", pageNo);
		return new ActionResult("guestbook.jsp");
	}

	private void pupulateRequest(HttpServletRequest request, Comment comment) {
		request.setAttribute("entityId", Long.toString(comment.getId()));
		request.setAttribute("reply", comment.getReply());
		request.setAttribute("content", comment.getContent());
		request.setAttribute("nickName", comment.getNickName());
		request.setAttribute("replayTime", Validator.dateToString(comment
				.getReplyTime()));
	}

	private void saveToDatabase(HttpServletRequest request,
			 boolean isEdit) {
		if (flag) {
			Comment comment;
			if (!isEdit) {
				comment = new Comment();
				comment.setCreateTime(new Date());				
				populateEntity(request, comment);
				facilityService.saveComment(comment);
			} else {
				comment = facilityService.findCommentById(request
						.getParameter("entityId"));
				if (!Validator.isEmpty(request.getParameter("reply"))) {
					comment.setReply(request.getParameter("reply"));
					comment.setReplyTime(new Date());
				}
				facilityService.updateComment(comment);
			}
		}
	}

	private boolean checkInputErrors(HttpServletRequest request){
		if(request.getParameter("content")==null ||request.getParameter("content").equals("") ){
			return false;
		}
		return true;
	}

	private void populateEntity(HttpServletRequest request, Comment comment) {
		comment.setNickName(getUserFromSession(request).getUserName());
		comment.setContent(request.getParameter("content"));
	}
}
