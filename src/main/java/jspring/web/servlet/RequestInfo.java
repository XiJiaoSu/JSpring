package jspring.web.servlet;

import jspring.web.servlet.config.annotation.RequestMethodType;

/**
 * 封装request请求头
 * @author will
 *
 */
public class RequestInfo {

	private String path;
	private RequestMethodType method;
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	public RequestMethodType getMethod() {
		return method;
	}

	public void setMethod(RequestMethodType method) {
		this.method = method;
	}
	
}
