package jspring.web.servlet.mvc;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jspring.web.servlet.FrameworkServlet;
import jspring.web.servlet.HandlerAdapter;
import jspring.web.servlet.HandlerExecutionChain;
import jspring.web.servlet.HandlerInterceptor;
import jspring.web.servlet.Model;
import jspring.web.servlet.ModelAndView;
import jspring.web.servlet.RequestInfo;
import jspring.web.servlet.context.support.AnnotationApplicationContext;
import jspring.web.servlet.handler.HandlerMethod;
import jspring.web.servlet.handler.SimpleControllerHanderAdapter;
import jspring.web.servlet.handler.SimpleUrlHanderMapping;
import jspring.web.servlet.view.InternalResourceViewResolver;
import jspring.web.servlet.view.View;
import jspring.web.servlet.view.ViewResolver;

public class DispatcherServlet extends FrameworkServlet {

	private static final long serialVersionUID = 1L;

	private SimpleUrlHanderMapping handlerMapping;

	private HandlerAdapter handlerAdapter;

	private InternalResourceViewResolver viewResolver;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		try {
			AnnotationApplicationContext.getInstance().loadPackageConfig("jspring.home").onCreate();
			handlerMapping = new SimpleUrlHanderMapping();
			handlerMapping.init();
			handlerAdapter = new SimpleControllerHanderAdapter();
			
			viewResolver=new InternalResourceViewResolver();
			viewResolver.setPrefix("/");
			viewResolver.setSuffix(".jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据request与response进行请求分发
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestInfo requestInfo = parseRequestInfo(request);
		HandlerMethod handlerMethod = handlerMapping.getMethodMappings().get(requestInfo);
		// 根据path+request method无法定位处理方法，则返回415
		if (handlerMethod == null) {
			response.setStatus(415);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("请求资源找不到!");
			return;
		}
		// 获取执行链
		HandlerExecutionChain executionChain = handlerMapping.getHandler(request);
		List<HandlerInterceptor> interceptors = executionChain.getInterceptors();
		// 前置拦截器
		for (HandlerInterceptor interceptor : interceptors) {
			interceptor.preHandle(request, response, handlerMethod.getObject());
		}

		// handler处理的核心方法:controller
		ModelAndView mv = handlerAdapter.handle(request, response, handlerMethod);
		
//		response.getOutputStream().write(new String("Hello").getBytes());

		// post拦截器
		for (HandlerInterceptor interceptor : interceptors) {
			interceptor.postHandle(request, response, handlerMethod.getObject(), mv);
		}
		response.getWriter().write(request.getMethod() + "\t" + request.getServletPath());
		// after拦截器
		for (HandlerInterceptor interceptor : interceptors) {
			interceptor.afterCompletion(request, response, handlerMethod.getObject(),null);
		}
		String viewName=mv.getViewName();
		Model model=mv.getModel();
		View view=viewResolver.resolveViewName(viewName);
		view.render(model, request, response);
	}


}
