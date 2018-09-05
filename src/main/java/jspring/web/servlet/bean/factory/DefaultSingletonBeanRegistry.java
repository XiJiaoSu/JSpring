package jspring.web.servlet.bean.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jspring.web.servlet.bean.config.SingletonBeanRegistry;

/**
 * 单例默认实现
 * @author WILLS
 *
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

	//存储单例对象的容器
	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
	
	@Override
	public void registerSingleton(String beanName, Object singletonObject) {
		singletonObjects.put(beanName, singletonObject);
	}

	@Override
	public Object getSingleton(String beanName) {
		return singletonObjects.get(beanName);
	}

	@Override
	public boolean containsSingleton(String beanName) {
		return singletonObjects.containsKey(beanName);
	}

}
