package jspring.web.servlet.context.support;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import jspring.web.servlet.bean.BeanFactory;
import jspring.web.servlet.config.PackageScan;
import jspring.web.servlet.config.annotation.Controller;

/**
 * 
 * @author WIllS 注解的默认容器实现
 *
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {
	
	private final PackageScan packageScan=new PackageScan();
	
	private final Set<Class<?>> annotatedClasses = 
			new LinkedHashSet<Class<?>>();
	//集合，用于存放配置了@Controller注解的class的实例
	private final Map<String,Object> controllers=new HashMap<String, Object>();
	
	private static AnnotationApplicationContext applicatonContext;
	
	private AnnotationApplicationContext(){
		
	}
	
	public static  AnnotationApplicationContext getInstance(){
		if (applicatonContext==null) {
			applicatonContext=new AnnotationApplicationContext();
		}
		return applicatonContext;
	}
	
	public AnnotationApplicationContext loadPackageConfig(String ...basePackages){
		if (basePackages!=null||basePackages.length>0) {
			for(String packages:basePackages){
				packageScan.addPackage(packages);
			}
		}
		annotatedClasses.addAll(packageScan.loadAllAnnotationClass());
		return this;
	}
	
	
	
	@Override
	public void onCreate() throws Exception {
		initControllers();
	}
	
	
	
	public Map<String, Object> getControllers() {
		return controllers;
	}

	private void initControllers(){
		for(Class<?> clazz:annotatedClasses){
			if (clazz.isAnnotationPresent(Controller.class)) {//将所有的拥有@Controller的类实例化
				try {
					controllers.put(clazz.getName(), clazz.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				
				}
			}
		}
	}
	
	@Override
	protected void setId(String id) {
		
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
		return null;
	}

	@Override
	public Object getBean(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
