package jspring.web.servlet.context.support;

import java.util.Map;

import jspring.web.servlet.bean.BeanFactory;
import jspring.web.servlet.bean.config.ConfigurableListableBeanFactory;
import jspring.web.servlet.bean.factory.DefaultListableBeanFactory;
import jspring.web.servlet.context.ConfigurableApplicationContext;

public abstract class AbstractApplicationContext extends ConfigurableApplicationContext{

	//bean factory for this context
	private DefaultListableBeanFactory beanFactory;
	
	//Synchronization monitor for the internal BeanFactory
	private Object beanFactoryMonitor=new Object();
	
	/**
	 * get default bean factory
	 * @return
	 * @throws IllegalStateException
	 */
	public  DefaultListableBeanFactory getBeanFactory() throws IllegalStateException{
		synchronized (this.beanFactoryMonitor) {
			if (this.beanFactory == null) {
				throw new IllegalStateException("BeanFactory not initialized or already closed");
			}
			return this.beanFactory;
		}
	}
	
	@Override
	public BeanFactory getParentBeanFactory() {
		
		return null;
	}

	@Override
	public boolean containsLocalBean(String name) {
		
		return false;
	}
	
	
	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws Exception {
		return getBeanFactory().getBeansOfType(type, includeNonSingletons, allowEagerInit);
	}

	@Override
	protected void setId(String id) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 实例化Beanfactory
	 * @param beanFactory
	 */
	public void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		if (beanFactory==null) {
			this.beanFactory=new DefaultListableBeanFactory();
		}else{
			this.beanFactory=beanFactory;
		}
	}
	
	
}
