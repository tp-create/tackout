package cn.htzb.easybuy.web;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.htzb.easybuy.entity.User;
import cn.htzb.easybuy.util.Validator;

@WebFilter(urlPatterns = {"/manage/Category","/manage/Product","/manage/GuestBook","/manage/News"}) 
public class AdminPowerFilter implements Filter {	
	public void destroy() {		
	}
	//过滤以上属于管理员的操作，若是普通用户或未登录则跳转到登录页
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpSession session = req.getSession();
		User user=(User) session.getAttribute("loginUser");//获取当前登录用户
		if(null==user || !(user.isAdministrator())){//是普通用户或未登录则跳转到登录页
			Validator validator = new Validator(Validator
					.toSingleParameters(req));
			validator.addError("userId", "用户无此权限");
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
			return;
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {		
	}	
}
