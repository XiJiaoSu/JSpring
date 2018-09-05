package jspring.web.servlet.handler;

import javax.servlet.http.HttpServletRequest;

import jspring.web.servlet.HandlerExecutionChain;
import jspring.web.servlet.HandlerMapping;
import jspring.web.servlet.interceptor.SimpleHandlerInterceptor;

public abstract class AbstractHandlerMapping implements HandlerMapping{

	//HandlerMethod
	private Object defaultHandler;
	
	
	
	public Object getDefaultHandler() {
		return defaultHandler;
	}


	public void setDefaultHandler(Object defaultHandler) {
		this.defaultHandler = defaultHandler;
	}


	@Override
	public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		//handlerMethod
		Object handler = getHandlerInternal(request);
		if (handler == null) {
			handler = getDefaultHandler();
		}
		if (handler == null) {
			return null;
		}
		HandlerExecutionChain executionChain = getHandlerExecutionChain(handler, request);
		return executionChain;
	}

	/**
	 * 获取执行链(handlerMethod,interceptors)
	 * @param handler
	 * @param request
	 * @return
	 */
	protected HandlerExecutionChain getHandlerExecutionChain(Object handler, HttpServletRequest request) {
		HandlerExecutionChain chain = (handler instanceof HandlerExecutionChain ?
				(HandlerExecutionChain) handler : new HandlerExecutionChain(handler));
		chain.addInterceptor(new SimpleHandlerInterceptor());//add one demo interceptor ,to be done 
		return chain;
	}
	
	protected abstract Object getHandlerInternal(HttpServletRequest request) throws Exception;
}
