package jspring.web.servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * handler 执行链包含handler与任意数量的handler的拦截器
 * returned by HandderMappping's{@link HandlerMapping#getHandler(javax.servlet.http.HttpServletRequest)}}method
 * 
 * 
 * @author wills
 *
 */
public class HandlerExecutionChain {
	
	private List<HandlerInterceptor> interceptors=new ArrayList<HandlerInterceptor>();
	
	
	public void addInterceptor(HandlerInterceptor interceptor){
		interceptors.add(interceptor);
	}


	public List<HandlerInterceptor> getInterceptors() {
		return interceptors;
	}
	
	
}
