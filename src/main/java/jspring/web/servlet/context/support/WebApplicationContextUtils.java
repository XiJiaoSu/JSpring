package jspring.web.servlet.context.support;

import javax.servlet.ServletContext;

import jspring.web.servlet.context.WebApplicationContext;

/**
 * ApplicationContext Utils
 * @author WILLS
 *
 */
public class WebApplicationContextUtils {
	
	/**
	 * get rootContext from ServletContext
	 * @param sc
	 * @return
	 */
	public static WebApplicationContext getWebApplicationContext(ServletContext sc) {
		return (WebApplicationContext) sc.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}
	
}
