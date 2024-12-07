package com.buoi2.ltw.interceptor;

import com.buoi2.ltw.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;




@Service
public class SecurityInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler)	throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("user") != null){
			if(!((Account) session.getAttribute("user")).isAdmin()){
				response.sendRedirect(request.getContextPath() + "/");
				return false;
			} else {
				return true;
			}
		}

		response.sendRedirect(request.getContextPath() + "/");
		return false;
	}
}
