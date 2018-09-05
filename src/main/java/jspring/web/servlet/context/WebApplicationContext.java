package jspring.web.servlet.context;

/**
 * Spring mvc 的容器
 * @author WILLS
 *
 */
public interface WebApplicationContext extends ApplicationContext{

	String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";
	
	
}
