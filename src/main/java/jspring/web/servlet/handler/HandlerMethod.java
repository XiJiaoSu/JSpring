package jspring.web.servlet.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerMethod {

	private Object object;
	
	private Method method;

	private List<Object> params=new ArrayList<Object>();
	
	public HandlerMethod() {
		super();
	}
	
	public HandlerMethod(Object object, Method method) {
		this.object = object;
		this.method = method;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	
	
}
