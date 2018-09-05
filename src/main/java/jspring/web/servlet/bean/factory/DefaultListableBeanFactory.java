package jspring.web.servlet.bean.factory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jspring.web.servlet.bean.config.ConfigurableListableBeanFactory;

/**
 * beanfactory的默认实现
 * @author WILLS
 *
 */
public class DefaultListableBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableListableBeanFactory{

	private final Map<Class<?>, List<String>> singletonBeanNamesByType = new ConcurrentHashMap<Class<?>, List<String>>(64);
	
	/**
	 * 根据类型获取bean
	 * @param type
	 * @param includeNonSingletons
	 * @param allowEagerInit
	 * @return
	 */
	public List<String> getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
		return singletonBeanNamesByType.get(type);
	}
	
	/**
	 * 从bean factory中获取bean实例
	 */
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws Exception {
		List<String> beanNames=getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
		Map<String, T> result = new LinkedHashMap<>(beanNames.size());
		for (String beanName : beanNames) {
			try {
				Object beanInstance = getBean(beanName);
				result.put(beanName, (T) beanInstance);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Object getBean(String beanName) throws Exception {
		return getSingleton(beanName);
	}

	/**
	 * 根据对象类型将，bean name 存起来
	 * @param name
	 * @param clazz
	 */
	public void registerBeanNamesByType(String name,Class<?> clazz){
		if (this.singletonBeanNamesByType.get(clazz)==null) {
			this.singletonBeanNamesByType.put(clazz,new ArrayList<String>());
		}
		this.singletonBeanNamesByType.get(clazz).add(name);
	}
	
//	getBeansOfType
	
}
