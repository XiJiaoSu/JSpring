package jspring.web.servlet.context.support;

import java.util.Map;

import jspring.web.servlet.bean.BeanFactory;
import jspring.web.servlet.context.ApplicationContext;

/**
 * Spring MVC Application 的xml配置默认实现
 * @author WILLS
 *
 */
public class XmlWebApplicationContext extends AbstractWebApplicationContext{

	public static final String DEFAULT_CONFIG_LOCATION = "/WEB-INF/applicationContext.xml";
	
	

	@Override
	public Object getBean(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BeanFactory getParentBeanFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsLocalBean(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 获取默认的配置路径
	 * @return
	 */
	protected String[] getDefaultConfigLocations() {
		return new String[] {DEFAULT_CONFIG_LOCATION};

	}
	
}
