package jspring.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import jspring.web.servlet.bean.BeanWrapper;
import jspring.web.servlet.bean.factory.DefaultListableBeanFactory;
import jspring.web.servlet.config.annotation.RequestMethodType;
import jspring.web.servlet.context.ApplicationContext;
import jspring.web.servlet.context.WebApplicationContext;
import jspring.web.servlet.context.support.AbstractWebApplicationContext;
import jspring.web.servlet.context.support.WebApplicationContextUtils;
import jspring.web.servlet.context.support.XmlWebApplicationContext;

/**
 * 
 * @author wills
 *
 */
public abstract class FrameworkServlet extends HttpServlet {
	private static final Logger logger = Logger.getLogger(FrameworkServlet.class);
	
	// web mvc 的bean容器
	private WebApplicationContext webApplicationContext;

	public static final Class<?> DEFAULT_CONTEXT_CLASS = XmlWebApplicationContext.class;

	private Class<?> contextClass = DEFAULT_CONTEXT_CLASS;

	// 配置文件的位置
	private String contextConfigLocation;

	@Override
	public void init() throws ServletException {
		logger.info("jspring.web.servlet.FrameworkServlet.init()");
		try {
			initBeanWrapper(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initServletBean();
	}

	/**
	 * Initialize the BeanWrapper for this HttpServletBean, possibly with custom
	 * editors.
	 */
	protected void initBeanWrapper(BeanWrapper bw) throws Exception {
	}

	/**
	 * Subclasses may override this to perform custom initialization. All bean
	 * properties of this servlet will have been set before this method is
	 * invoked.
	 * <p>
	 * This default implementation is empty.
	 * 
	 * @throws ServletException
	 *             if subclass initialization fails
	 */
	protected void initServletBean() throws ServletException {
		logger.info("jspring.web.servlet.FrameworkServlet.initServletBean()");
		getServletContext().log("Initializing Spring FrameworkServlet '" + getServletName() + "'");
		long startTime = System.currentTimeMillis();
		this.webApplicationContext = initWebApplicationContext();
		onRefresh(this.webApplicationContext);
		logger.info("start times :"+ (System.currentTimeMillis()-startTime));
	}

	/**
	 * 初始化webApplicationContext容器
	 * 
	 * @return
	 */
	protected WebApplicationContext initWebApplicationContext() {
		logger.info("jspring.web.servlet.FrameworkServlet.initWebApplicationContext()");
		// 父容器在放在ServletContext中，获取父容器
		WebApplicationContext rootContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		WebApplicationContext wac = null;
		if (wac == null) {
			wac = createWebApplicationContext(rootContext);
		}
		return wac;
	}

	/**
	 * 利用父容器去实例化 MVC的容器
	 * 
	 * @param parent
	 * @return
	 */
	protected WebApplicationContext createWebApplicationContext(WebApplicationContext parent) {
		
		return createWebApplicationContext((ApplicationContext) parent);
	}

	/**
	 * init mvc webapplication details
	 * 
	 * @param parent
	 * @return
	 */
	protected WebApplicationContext createWebApplicationContext(ApplicationContext parent) {

		Class<?> contextClass = getContextClass();
		AbstractWebApplicationContext wac = null;
		try {
			wac = (AbstractWebApplicationContext) contextClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (wac == null) {
			wac = new XmlWebApplicationContext();
		}
		configureAndRefreshWebApplicationContext(wac);
		return wac;
	}

	/**
	 * 配置ApplicationContext
	 * 
	 * @param wac
	 */
	protected void configureAndRefreshWebApplicationContext(AbstractWebApplicationContext wac) {
		logger.info("jspring.web.servlet.FrameworkServlet.configureAndRefreshWebApplicationContext(AbstractWebApplicationContext)");
		wac.customizeBeanFactory(new DefaultListableBeanFactory());//初始化 beanfactory
		try {
			Class clazz = Class.forName("jspring.web.servlet.handler.RequestMappingInfoHandlerMapping");
			HandlerMapping handlerMapping=(HandlerMapping) clazz.newInstance();
			wac.getBeanFactory().registerSingleton("hanlderMapping#1", handlerMapping);
			wac.getBeanFactory().registerBeanNamesByType("hanlderMapping#1", Class.forName("jspring.web.servlet.HandlerMapping"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	@Override
	protected void doTrace(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doService(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据request生成handlerMethod 的key
	 * 
	 * @param req
	 * @return
	 */
	protected RequestMappingInfo parseRequestInfo(HttpServletRequest req) {
		String requestMethod = req.getMethod().toUpperCase();
		RequestMappingInfo info = new RequestMappingInfo();
		String servletPath = req.getServletPath();
		System.out.println(servletPath);
		if (servletPath.length() > 0) {
			info.setPath(servletPath.substring(1));
		} else {
			info.setPath("");
		}
		System.out.println(info);
		switch (requestMethod) {
		case "GET":
			info.setMethod(RequestMethodType.GET);
			break;
		case "POST":
			info.setMethod(RequestMethodType.POST);
			break;
		case "PUT":
			info.setMethod(RequestMethodType.PUT);
			break;
		case "DELETE":
			info.setMethod(RequestMethodType.DELETE);
			break;
		default:
			break;
		}
		return info;
	}

	public Class<?> getContextClass() {
		return contextClass;
	}

	public void setContextClass(Class<?> contextClass) {
		this.contextClass = contextClass;
	}

	protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;

	protected void onRefresh(ApplicationContext context) {

	}

}
