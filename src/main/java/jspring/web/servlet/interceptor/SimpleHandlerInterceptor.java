package jspring.web.servlet.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jspring.web.servlet.HandlerInterceptor;
import jspring.web.servlet.ModelAndView;

public class SimpleHandlerInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("jspring.web.servlet.interceptor.SimpleHandlerInterceptor.preHandle(HttpServletRequest, HttpServletResponse, Object)");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("jspring.web.servlet.interceptor.SimpleHandlerInterceptor.postHandle(HttpServletRequest, HttpServletResponse, Object, ModelAndView)");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("jspring.web.servlet.interceptor.SimpleHandlerInterceptor.afterCompletion(HttpServletRequest, HttpServletResponse, Object, Exception)");
	}

	
	
}
