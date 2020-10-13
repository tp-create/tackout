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

@WebFilter(urlPatterns = {"/manage/Order","/manage/User","/GuestBook"}) 
public class UserPowerFilter implements Filter {
	
	public void destroy() {		
	}
	//过滤以上地址，如果未登录则无此权限
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		HttpSession session = req.getSession();
		User user=(User) session.getAttribute("loginUser");//获取当前用户
		if(null==user){			
			Validator validator = new Validator(Validator
					.toSingleParameters(req));
			validator.addError("userId", "尚未登录");
			req.setAttribute("errors", validator.getErrors());
			req.getRequestDispatcher("/login.jsp").forward(req, resp);			
			return;			
		}
		chain.doFilter(request, response);
	}
	public void init(FilterConfig filterConfig) throws ServletException {		
	}	
}
