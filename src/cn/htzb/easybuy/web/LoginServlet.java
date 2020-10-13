package cn.htzb.easybuy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.htzb.easybuy.biz.UserService;
import cn.htzb.easybuy.biz.impl.UserServiceImpl;
import cn.htzb.easybuy.entity.ShoppingCart;
import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.Validator;

@WebServlet(urlPatterns = { "/Login" }, name = "Login")
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
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
		String action = req.getParameter("action");

		if ("logout".equals(action)) {//退出
			User user=(User)req.getSession().getAttribute("loginUser");
			userService.setLogin(user, false);//设置当前为非登录状态
			req.getSession().removeAttribute("loginUser");
			// 清除购物车
			req.getSession().removeAttribute("cart");
			req.getSession().removeAttribute("cart2");
			//清除最近浏览
			//req.getSession().removeAttribute("recentViewProducts");
			resp.sendRedirect("Home");
		} else {//登录
			Validator validator = new Validator(
					Validator.toSingleParameters(req));
			String userName = req.getParameter("userId");
			String password = req.getParameter("password");
			String incode = (String) req.getParameter("code");
			String rightcode = (String) req.getSession()
					.getAttribute("numrand");// 汉字验证码的话则是hanzirand
			if (incode != null && rightcode != null && !rightcode.equals(incode)) {				
					req.setAttribute("userId",userName);
					req.setAttribute("password",password);
					validator.addError("mess", "验证码错误，请重新输入!");
				
			} else if (!validator.checkRequiredError(new String[] { "userId",
					"password" })) {				
				User user = userService.findById(userName);
				if (user == null)
					validator.addError("userId", "用户不存在");
				else {
					if (user.getPassword().equals(password)) {
						userService.setLogin(user, true);//设置当前为登录状态
						req.getSession().setAttribute("loginUser", user);
					} else {
						validator.addError("password", "密码不正确");
					}
				}
			}

			if (!validator.hasErrors()) {
				ShoppingCart cart = (ShoppingCart) req.getSession()
						.getAttribute("cart");
				if (cart != null && cart.items != null && cart.items.size() > 0) {// 先购物后登录
					resp.sendRedirect(req.getContextPath() + "/Cart");
				} else {// 尚未购物
					resp.sendRedirect(req.getContextPath() + "/Home");
				}
			} else {
				req.setAttribute("errors", validator.getErrors());
				req.getRequestDispatcher("login.jsp").forward(req, resp);
			}
		}
	}
}
