package jspring.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @author hander
 *
 */
public interface HandlerAdapter {

	
	ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

	
}
