package jspring.web.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import jspring.web.servlet.bean.factory.BeanFactoryUtils;
import jspring.web.servlet.context.ApplicationContext;
import jspring.web.servlet.handler.RequestMappingInfoHandlerMapping;
import jspring.web.servlet.view.InternalResourceViewResolver;
import jspring.web.servlet.view.View;

public class DispatcherServlet extends FrameworkServlet {
	
	private static final Logger logger=Logger.getLogger(DispatcherServlet.class);
	
	private static final long serialVersionUID = 1L;

	private List<HandlerMapping> handlerMappings;

	private HandlerAdapter handlerAdapter;

	private InternalResourceViewResolver viewResolver;

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("jspring.web.servlet.DispatcherServlet.init(ServletConfig)");
		// TODO Auto-generated method stub
//		System.out.println("init----------------------");
//		String initParameter = config.getInitParameter("contextConfigLocation");
//		System.out.println(initParameter);
		super.init(config);
		// try {
		// AnnotationApplicationContext.getInstance().loadPackageConfig("jspring.home").onCreate();
		// handlerMappings = new ArrayList<HandlerMapping>();
		// // handlerMapping = new SimpleUrlHanderMapping();
		// // handlerMapping.init();
		// handlerMappings.add(new SimpleUrlHanderMapping().init());
		// handlerAdapter = new SimpleControllerHanderAdapter();
		//
		// viewResolver = new InternalResourceViewResolver();
		// viewResolver.setPrefix("/");
		// viewResolver.setSuffix(".jsp");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	@Override
	protected void onRefresh(ApplicationContext context) {
		initStrategies(context);
	}

	protected void initStrategies(ApplicationContext context) {
		logger.info("jspring.web.servlet.DispatcherServlet.initStrategies(ApplicationContext)");
		initHandlerMappings(context);
	}

	/**
	 * 初始化 HandlerMappings
	 * 
	 * @param context
	 */
	private void initHandlerMappings(ApplicationContext context) {
		this.handlerMappings = null;
		
		try {
			// 从容器中寻找HandlerMapping
			Map<String, HandlerMapping> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(context,
					HandlerMapping.class, false, true);
			if (!matchingBeans.isEmpty()) {//HandlerMapping 不为空
				this.handlerMappings = new ArrayList<>(matchingBeans.values());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.handlerMappings == null) {//找不到，添加默认的实现
			 this.handlerMappings =new ArrayList<HandlerMapping>();
			 this.handlerMappings.add(new RequestMappingInfoHandlerMapping());
		}
		// this.handlerMappings =new ArrayList<HandlerMapping>();
		// this.handlerMappings.add(new RequestMappingInfoHandlerMapping());
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		doDispatch(request, response);
	}

	/**
	 * 根据request与response进行请求分发
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(request.getClass());

		HandlerExecutionChain executionChain = null;
		try {
			// 获取执行链
			executionChain = getHandler(request);

			if (executionChain == null) {
				noHandlerFound(request, response);
				return;
			}
			// 前置拦截器
			if (executionChain.applyPreHandle(request, response)) {
				return;
			}
			// handler处理的核心方法:controller
			ModelAndView mv = handlerAdapter.handle(request, response, executionChain.getHandler());

			executionChain.applyPostHandle(request, response, mv);

			String viewName = mv.getViewName();
			Model model = mv.getModel();
			View view = viewResolver.resolveViewName(viewName);
			view.render(model, request, response);

		} catch (Exception ex) {
			triggerAfterCompletion(request, response, executionChain, ex);
		}
	}

	private void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response,
			HandlerExecutionChain mappedHandler, Exception ex) throws Exception {

		if (mappedHandler != null) {
			mappedHandler.triggerAfterCompletion(request, response, ex);
		}
		throw ex;
	}

	/**
	 * Return the HandlerExecutionChain for this request.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		for (HandlerMapping hm : handlerMappings) {
			HandlerExecutionChain handler = hm.getHandler(request);
			if (handler != null) {
				return handler;
			}
		}
		return null;
	}

	protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);

	}
}
