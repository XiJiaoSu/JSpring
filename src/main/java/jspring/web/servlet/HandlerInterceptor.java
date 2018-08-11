package jspring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerInterceptor {
	
	/**
	 * 返回值为false时，执行链将会断开
	 * @param request
	 * @param response
	 * @param handler
	 * @return 
	 * @throws Exception
	 */
	default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		return true;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 */
	default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            ModelAndView modelAndView) throws Exception {
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 */
	default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 Exception ex) throws Exception {
	}
	
}
