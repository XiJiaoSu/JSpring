package jspring.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author wills
 *
 */
public interface HandlerMapping {

	
	
	/**
	 * 根据request获取handler的执行链
	 * @param request
	 * @return
	 * @throws Exception
	 */
	HandlerExecutionChain getHandler(HttpServletRequest request)throws Exception;
	
}
