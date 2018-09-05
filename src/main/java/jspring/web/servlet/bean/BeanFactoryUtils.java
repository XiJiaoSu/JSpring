package jspring.web.servlet.bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class BeanFactoryUtils {

	
	public static <T> Map<String, T> beansOfTypeIncludingAncestors(
			ListableBeanFactory lbf, Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws Exception {

		Map<String, T> result = new LinkedHashMap<>(4);
		result.putAll(lbf.getBeansOfType(type, includeNonSingletons, allowEagerInit));
//		if (lbf instanceof HierarchicalBeanFactory) {
//			HierarchicalBeanFactory hbf = (HierarchicalBeanFactory) lbf;
//			if (hbf.getParentBeanFactory() instanceof ListableBeanFactory) {
//				Map<String, T> parentResult = beansOfTypeIncludingAncestors(
//						(ListableBeanFactory) hbf.getParentBeanFactory(), type, includeNonSingletons, allowEagerInit);
//				parentResult.forEach((beanName, beanType) -> {
//					if (!result.containsKey(beanName) && !hbf.containsLocalBean(beanName)) {
//						result.put(beanName, beanType);
//					}
//				});
//			}
//		}
		return result;
	}
	
	
}
