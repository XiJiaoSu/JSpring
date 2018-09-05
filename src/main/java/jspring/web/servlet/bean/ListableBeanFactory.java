package jspring.web.servlet.bean;

import java.util.Map;

/**
 * 
 * @author Wills
 *
 */
public interface ListableBeanFactory extends BeanFactory{

	
	<T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws Exception;
}
