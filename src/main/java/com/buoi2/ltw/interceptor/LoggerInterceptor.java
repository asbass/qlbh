package com.buoi2.ltw.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Service
public class LoggerInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler)	throws Exception {
		System.out.println("LoggerInterceptor.preHandle()");
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler,	ModelAndView modelAndView) throws Exception {
		System.out.println("LoggerInterceptor.postHandle()");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response, Object handler, Exception ex)	throws Exception {
		System.out.println("LoggerInterceptor.afterCompletion()");
	}
}
