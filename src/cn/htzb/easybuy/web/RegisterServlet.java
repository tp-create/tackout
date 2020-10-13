package cn.htzb.easybuy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.htzb.easybuy.biz.UserService;
import cn.htzb.easybuy.biz.impl.UserServiceImpl;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.Validator;
@WebServlet(urlPatterns = {"/Register"},name = "Register")
public class RegisterServlet extends HttpServlet {
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
		Validator validator = new Validator(Validator.toSingleParameters(req));
		saveUserToDatabase(req, validator);

		if (!validator.hasErrors()) {
			req.setAttribute("msg", "已经成功注册请登录");
			req.getRequestDispatcher("message.jsp").forward(req, resp);
		} else {
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher("register.jsp").forward(req, resp);
		}
	}

	//注册的用户向数据库保存
	private void saveUserToDatabase(HttpServletRequest request,
			Validator validator) {
		try {
			User user = new User();
			user.setStatus(User.USER_TYPE_ORDINARY);
			user.setUserId(request.getParameter("userId"));
			user.setUserName(request.getParameter("userName"));
			user.setMale(request.getParameter("sex").equals("male"));
			user.setPassword(request.getParameter("password"));
			user.setBirthday(Validator.convertToDate(request
					.getParameter("birthday")));
			user.setIdentityCode(request.getParameter("identityCode"));
			user.setEmail(request.getParameter("email"));
			user.setMobile(request.getParameter("mobile"));
			user.setAddress(request.getParameter("address"));
			userService.save(user);
		} catch (Exception e) {
			validator.addError("userId", "用户名字已经存在");
		}
	}

	//检查输入项是否正确
	private void checkInputErrors(HttpServletRequest request,
			Validator validator) {
		validator.checkRequiredError(new String[] { "userId", "userName",
				"password", "confirmPassword", "sex", "address" });
		if (!validator.hasErrors()) {
			String confirmPassword = request.getParameter("confirmPassword");
			String password = request.getParameter("password");
			if (!password.equals(confirmPassword)) {
				validator.addError("_form", "输入口令不一致");
			}
			validator.checkDateFormatError("birthday", true);
		}
	}
}
