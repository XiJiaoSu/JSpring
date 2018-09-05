package jspring.web.servlet.bean.factory;

import java.util.LinkedHashMap;
import java.util.Map;

import jspring.web.servlet.bean.ListableBeanFactory;

public class BeanFactoryUtils {

	/**
	 *  依据Class从容器中寻找指定的对象，找不到向父容器找
	 * @param lbf
	 * @param type
	 * @param includeNonSingletons
	 * @param allowEagerInit
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, T> beansOfTypeIncludingAncestors(
			ListableBeanFactory lbf, Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws Exception {
		Map<String, T> result = new LinkedHashMap<>(4);
		result.putAll(lbf.getBeansOfType(type, false, true));
		return result;
		
	}
	
}
