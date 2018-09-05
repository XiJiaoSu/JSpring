package jspring.web.servlet.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jspring.web.servlet.HandlerExecutionChain;
import jspring.web.servlet.HandlerMapping;
import jspring.web.servlet.RequestMappingInfo;
import jspring.web.servlet.config.annotation.RequestMapping;
import jspring.web.servlet.config.annotation.RequestMethodType;
import jspring.web.servlet.context.support.AnnotationApplicationContext;
import jspring.web.servlet.interceptor.SimpleHandlerInterceptor;

/**
 * 简单的地址映射
 * @author wills
 *
 */
public class SimpleUrlHanderMapping extends AbstractHandlerMethodMapping<RequestMappingInfo>{
	
	AnnotationApplicationContext applicatonContext = AnnotationApplicationContext.getInstance();
	private HandlerExecutionChain chain;
	/**
	 * 根据Request,寻找Controller中的对应的Method
	 */
	private Map<RequestMappingInfo,HandlerMethod> methodMappings=new HashMap<RequestMappingInfo,HandlerMethod>();
	
	
	public SimpleUrlHanderMapping() {
		
	}
	
	public SimpleUrlHanderMapping init(){
		loadHandlerMapping();
		//to be done
//		chain=new HandlerExecutionChain();
//		chain.addInterceptor(new SimpleHandlerInterceptor());
		return this;
	}
	
	private void loadHandlerMapping(){
		for(Entry<String, Object> entry:applicatonContext.getControllers().entrySet()){
			Method[] methods = entry.getValue().getClass().getDeclaredMethods();
			for(Method method:methods){
				if(method.isAnnotationPresent(RequestMapping.class)){
					//将带有@RequestMapping的Method存在集合中做映射
					RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
					RequestMappingInfo requestInfo = new RequestMappingInfo();
					requestInfo.setMethod(requestMapping.method());
					requestInfo.setPath(requestMapping.value());
					methodMappings.put(requestInfo, new HandlerMethod(entry.getValue(),method));
				}
			}
		}
	}
	
	public Map<RequestMappingInfo, HandlerMethod> getMethodMappings() {
		return methodMappings;
	}

	/**
	 * 返回执行链
	 */
	public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
		return chain;
	}

	public static void main(String[] args) throws Exception {
		AnnotationApplicationContext.getInstance().loadPackageConfig("jspring.home").onCreate();
		SimpleUrlHanderMapping handlerMapping=new SimpleUrlHanderMapping();
		handlerMapping.init();
		RequestMappingInfo requestInfo=new RequestMappingInfo();
		requestInfo.setMethod(RequestMethodType.GET);
		requestInfo.setPath("user");
		for(Entry<RequestMappingInfo,HandlerMethod> entry:handlerMapping.getMethodMappings().entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		HandlerMethod handlerMethod = handlerMapping.getMethodMappings().get(requestInfo);
		System.out.println(handlerMethod);
		handlerMethod.getMethod().invoke(handlerMethod.getObject(),null);
		
	}

	@Override
	protected RequestMappingInfo getMatchingMapping(RequestMappingInfo mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Set<String> getMappingPathPatterns(RequestMappingInfo mapping) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
