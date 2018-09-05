package jspring.web.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * handler 执行链包含handler与任意数量的handler的拦截器 returned by HandderMappping's
 * {@link HandlerMapping#getHandler(javax.servlet.http.HttpServletRequest)}
 * }method
 * 
 * 
 * @author wills
 *
 */
public class HandlerExecutionChain {

	private List<HandlerInterceptor> interceptors = new ArrayList<HandlerInterceptor>();
	private final Object handler;

	public void addInterceptor(HandlerInterceptor interceptor) {
		interceptors.add(interceptor);
	}

	/**
	 * Create a new HandlerExecutionChain.
	 * @param handler the handler object to execute
	 */
	public HandlerExecutionChain(Object handler) {
		this(handler, (HandlerInterceptor[]) null);
	}

	/**
	 * Create a new HandlerExecutionChain.
	 * @param handler the handler object to execute
	 * @param interceptors the array of interceptors to apply
	 * (in the given order) before the handler itself executes
	 */
	public HandlerExecutionChain(Object handler, HandlerInterceptor... interceptors) {
		if (handler instanceof HandlerExecutionChain) {
			HandlerExecutionChain originalChain = (HandlerExecutionChain) handler;
			this.handler = originalChain.getHandler();
			this.interceptors = new ArrayList<HandlerInterceptor>();
			this.interceptors.addAll(((HandlerExecutionChain) handler).getInterceptors());
		}
		else {
			this.handler = handler;
			for (int i = 0; i < interceptors.length; i++) {
				this.interceptors.add(interceptors[i]);
			}
		}
	}
	
	public Object getHandler() {
		return handler;
	}

	private List<HandlerInterceptor> getInterceptors() {
		return interceptors;
	}

	/**
	 * Apply postHandle methods of registered interceptors.
	 * 
	 * @param request
	 * @param response
	 * @param mv
	 * @throws Exception
	 */
	void applyPostHandle(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) throws Exception {

		List<HandlerInterceptor> interceptors = getInterceptors();

		for (HandlerInterceptor interceptor : interceptors) {
			interceptor.postHandle(request, response, this.handler, mv);
		}

	}

	/**
	 * 执行前置拦截器
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<HandlerInterceptor> interceptors = getInterceptors();
		if (interceptors != null) {
			for (HandlerInterceptor interceptor : interceptors) {
				if (!interceptor.preHandle(request, response, handler)) {// 前置拦截器返回true才能继续执行下去
					return false;
				}
			}
		}
		return true;
	}

	void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {

		List<HandlerInterceptor> interceptors  = getInterceptors();
	
			for (HandlerInterceptor interceptor : interceptors) {
				try {
					interceptor.afterCompletion(request, response, this.handler, ex);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
}

