package jspring.web.servlet.bean.config;

/**
 * 注册单例对象
 * @author WILLS
 *
 */
public interface SingletonBeanRegistry {

	/**
	 * 添加单列对象
	 * @param beanName
	 * @param singletonObject
	 */
	void registerSingleton(String beanName, Object singletonObject);

	/**
	 * 根据beanName获取当前对应对象的名字
	 * @param beanName
	 * @return
	 */
	Object getSingleton(String beanName);
	
	/**
	 * 是否包含名字为beanName的对象
	 * @param beanName
	 * @return
	 */
	boolean containsSingleton(String beanName);
}
