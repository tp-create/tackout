package cn.htzb.easybuy.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import cn.htzb.easybuy.biz.UserService;
import cn.htzb.easybuy.biz.impl.UserServiceImpl;
import cn.htzb.easybuy.entity.Pager;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.ActionResult;
import cn.htzb.easybuy.util.Validator;

@WebServlet(urlPatterns = { "/manage/User", "/User" }, name = "User")
public class UserServlet extends HttpServlet {
	private UserService userService;

	public void init() throws ServletException {
		userService = new UserServiceImpl();
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
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("loginUser");
		if (actionIndicator == null && user.isAdministrator()) {// 如果是管理员登录
			actionIndicator = "list";
		} else if (actionIndicator == null && !user.isAdministrator()) {// 如果是普通用户登录
			actionIndicator = "self";
		}
		if ("read".endsWith(actionIndicator)) {//用户详情
			result = read(req, validator);
		} else if ("list".endsWith(actionIndicator)) {//查看所有用户
			result = list(req, validator);
		} else if ("create".endsWith(actionIndicator)) {//创建一个用户
			result = create(req, validator);
		} else if ("delete".endsWith(actionIndicator)) {//删除某用户
			result = delete(req, validator);
		} else if ("save".endsWith(actionIndicator)) {//保存某用户信息
			boolean isEdit = true;
			String editIndicator = req.getParameter("entityId");
			if (Validator.isEmpty(editIndicator))
				isEdit = false;
			result = save(req, validator, isEdit);
		} else if ("self".endsWith(actionIndicator)) {//查看用户
			req.setAttribute("user", user);
			result = new ActionResult("user.jsp");
		}

		if (!validator.hasErrors() && result.isRedirect()) {
		
			resp.sendRedirect(result.getViewName());
			
		} else {
			
				req.setAttribute("errors", validator.getErrors());	
				req.getRequestDispatcher(result.getViewName()).forward(req, resp);
			}
			
		}

	private ActionResult save(HttpServletRequest request, Validator validator,
			boolean isEdit) {
		checkInputErrors(request, validator);

		saveUserToDatabase(request, validator, isEdit);

		if (!validator.hasErrors()) {
			return new ActionResult("User", true);
		} else {
			pupulateRequest(request);
			return new ActionResult("user-modify.jsp");
		}
	}

	private void checkInputErrors(HttpServletRequest request,
			Validator validator) {
		validator.checkRequiredError(new String[] { "userId", "userName",
				"password", "confirmPassword", "sex", "address" });
		if (!validator.hasErrors()) {
			String confirmPassword = request.getParameter("confirmPassword");
			String password = request.getParameter("password");
			if (!password.equals(confirmPassword)) {
				validator.addError("confirmPassword", "输入口令不一致");
			}
			validator.checkDateFormatError("birthday", true);
		}
	}

	public ActionResult read(HttpServletRequest request, Validator validator) {
		User user = userService.findById(request.getParameter("userId"));
		String[]addr=user.getAddress().split(";");		
		request.setAttribute("addr", addr);
		pupulateRequest(request, user);
		return new ActionResult("user-modify.jsp");
	}

	public ActionResult list(HttpServletRequest request, Validator validator) {
		String page = request.getParameter("page");
		int pageNo = 1;
		if (!Validator.isEmpty(page))
			pageNo = Integer.parseInt(page);
		long rowCount = userService.getUserRowCount();
		Pager pager = new Pager(rowCount, pageNo);
		List<User> users = userService.getUsers(pager);
		request.setAttribute("users", users);
		request.setAttribute("pager", pager);
		request.setAttribute("pageNo", pageNo);
		return new ActionResult("user.jsp");
	}

	public ActionResult create(HttpServletRequest req, Validator validator) {
		return new ActionResult("user-modify.jsp");
	}

	public ActionResult delete(HttpServletRequest request, Validator validator) {
		userService.delete(request.getParameter("userId"));
		return new ActionResult("User", true);
	}

	private void pupulateRequest(HttpServletRequest request, User user) {
		request.setAttribute("userId", user.getUserId());
		request.setAttribute("userName", user.getUserName());
		request.setAttribute("sex", user.isMale() ? "male" : "female");
		request.setAttribute("password", user.getPassword());
		request.setAttribute("confirmPassword", user.getPassword());
		request.setAttribute("birthday",
				user.getBirthday());
		request.setAttribute("identityCode", user.getIdentityCode());
		request.setAttribute("email", user.getEmail());
		request.setAttribute("mobile", user.getMobile());
		request.setAttribute("address", user.getAddress());
	}

	private void pupulateRequest(HttpServletRequest request) {
		request.setAttribute("userId", request.getParameter("userId"));
		request.setAttribute("userName", request.getParameter("userName"));
		request.setAttribute("sex", request.getParameter("sex"));
		request.setAttribute("password", request.getParameter("password"));
		request.setAttribute("confirmPassword",
				request.getParameter("confirmPassword"));
		request.setAttribute("birthday", request.getParameter("birthday"));
		request.setAttribute("identityCode",
				request.getParameter("identityCode"));
		request.setAttribute("email", request.getParameter("email"));
		request.setAttribute("mobile", request.getParameter("mobile"));
		request.setAttribute("address", request.getParameter("address"));
	}

	private void saveUserToDatabase(HttpServletRequest request,
			Validator validator, boolean isEdit) {
		if (!validator.hasErrors()) {
			User user;
			try {
				if (!isEdit) {
					user = new User();
					user.setStatus(User.USER_TYPE_ORDINARY);
					populateEntity(request, user);
					userService.save(user);
					// 如果修改的是当前用户则改变session
					HttpSession session = request.getSession();
					User u = (User) session.getAttribute("loginUser");
					if (user.getUserId().equals(u.getUserId())) {
						session.setAttribute("loginUser", user);
					}
				} else {
					user = userService.findById(request
							.getParameter("entityId"));
					populateEntity(request, user);
					userService.update(user, request.getParameter("entityId"));
					// 如果修改的是当前用户则改变session
					HttpSession session = request.getSession();
					User u = (User) session.getAttribute("loginUser");
					if (user.getUserId().equals(u.getUserId())) {
						session.setAttribute("loginUser", user);
					}
				}
			} catch (Exception e) {
				validator.addError("userId", "用户名字已经存在");
			}
		}
	}

	private void populateEntity(HttpServletRequest request, User user) {
		user.setUserId(request.getParameter("userId"));
		user.setUserName(request.getParameter("userName"));
		user.setMale(request.getParameter("sex").equals("male"));
		user.setPassword(request.getParameter("password"));
		user.setBirthday(Validator.convertToDate(request
				.getParameter("birthday")));
		user.setIdentityCode(request.getParameter("identityCode"));
		user.setEmail(request.getParameter("email"));
		user.setMobile(request.getParameter("mobile"));
		String []addr=request.getParameterValues("address");
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<addr.length;i++){
			sb.append(addr[i]);
			if(i!=addr.length-1)
			sb.append(";");
		}
		user.setAddress(sb.toString());		
	}
}
