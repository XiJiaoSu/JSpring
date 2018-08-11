package jspring.web.servlet.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import jspring.web.servlet.HandlerExecutionChain;
import jspring.web.servlet.HandlerMapping;
import jspring.web.servlet.RequestInfo;
import jspring.web.servlet.config.annotation.RequestMapping;
import jspring.web.servlet.context.support.AnnotationApplicationContext;

/**
 * 简单的地址映射
 * @author wills
 *
 */
public class SimpleUrlHanderMapping implements HandlerMapping{
	
	AnnotationApplicationContext applicatonContext = AnnotationApplicationContext.getInstance();

	/**
	 * 根据Request,寻找Controller中的对应的Method
	 */
	private Map<RequestInfo,HandlerMethod> methodMappings=new HashMap<RequestInfo,HandlerMethod>();
	
	
	public SimpleUrlHanderMapping() {
		
	}
	
	public void init(){
		loadHandlerMapping();
	}
	
	private void loadHandlerMapping(){
		for(Entry<String, Object> entry:applicatonContext.getControllers().entrySet()){
			Method[] methods = entry.getValue().getClass().getDeclaredMethods();
			for(Method method:methods){
				if(method.isAnnotationPresent(RequestMapping.class)){
					//将带有@RequestMapping的Method存在集合中做映射
					RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
					RequestInfo requestInfo = new RequestInfo();
					requestInfo.setMethod(requestMapping.method());
					requestInfo.setPath(requestMapping.value());
					methodMappings.put(requestInfo, new HandlerMethod(entry.getValue(),method));
				}
			}
		}
	}
	
	public Map<RequestInfo, HandlerMethod> getMethodMappings() {
		return methodMappings;
	}

	public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		return null;
	}

}
