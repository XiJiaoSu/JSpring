package jspring.web.servlet.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jspring.web.servlet.FrameworkServlet;
import jspring.web.servlet.RequestInfo;
import jspring.web.servlet.context.support.AnnotationApplicationContext;
import jspring.web.servlet.handler.SimpleUrlHanderMapping;

public class DispatcherServlet extends FrameworkServlet {

	
	private static final long serialVersionUID = 1L;
	
	private SimpleUrlHanderMapping handlerMapping;
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try {
			AnnotationApplicationContext.getInstance().loadPackageConfig("jspring.home").onCreate();
			handlerMapping=new SimpleUrlHanderMapping();
			handlerMapping.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据request与response进行请求分发
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(request.getRequestURI());
		RequestInfo requestInfo = parseRequestInfo(request);
//		handlerMapping.
//		List<HandlerInterceptor> interceptors = handlerMapping.getHandler(request).getInterceptors();
//		for(HandlerInterceptor interceptor:interceptors){
//			interceptor.preHandle(request, response, handler);
//		}
		
		response.getOutputStream().write(new String("Hello").getBytes());
		
	}

	
	public static void main(String[] args) throws Exception {
		AnnotationApplicationContext.getInstance().loadPackageConfig("jspring.home").onCreate();
		SimpleUrlHanderMapping handerMapping = new SimpleUrlHanderMapping();
		handerMapping.init();
	}
	
}
