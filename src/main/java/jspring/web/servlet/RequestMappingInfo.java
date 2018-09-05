package jspring.web.servlet;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import jspring.web.servlet.config.annotation.RequestMethodType;

/**
 * 封装request请求头,作为key，获取指定的handlerMethod
 * 
 * @author will
 *
 */
public class RequestMappingInfo {

	private String path;
	private RequestMethodType method;
	
	private final Set<String> patterns;
	
	
	public RequestMappingInfo() {
		super();
		patterns=new HashSet<String>();
	}

	public RequestMappingInfo(Set<String> patterns) {
		super();
		this.patterns = patterns;
	}

	public RequestMappingInfo(String path, RequestMethodType method, Set<String> patterns) {
		super();
		this.path = path;
		this.method = method;
		this.patterns = patterns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestMappingInfo other = (RequestMappingInfo) obj;
		if (method != other.method)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

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

	public Set<String> getPatterns() {
		return this.patterns;
	}

	public static RequestMappingInfo getMatchingCondition(HttpServletRequest request) {
//		RequestMethodsRequestCondition methods = this.methodsCondition.getMatchingCondition(request);
//		ParamsRequestCondition params = this.paramsCondition.getMatchingCondition(request);
//		HeadersRequestCondition headers = this.headersCondition.getMatchingCondition(request);
//		ConsumesRequestCondition consumes = this.consumesCondition.getMatchingCondition(request);
//		ProducesRequestCondition produces = this.producesCondition.getMatchingCondition(request);

//		if (methods == null || params == null || headers == null || consumes == null || produces == null) {
//			return null;
//		}
//
//		PatternsRequestCondition patterns = this.patternsCondition.getMatchingCondition(request);
//		if (patterns == null) {
//			return null;
//		}
//
//		RequestConditionHolder custom = this.customConditionHolder.getMatchingCondition(request);
//		if (custom == null) {
//			return null;
//		}

		return parseRequestInfo(request);
	}

	protected static RequestMappingInfo parseRequestInfo(HttpServletRequest req) {
		String requestMethod = req.getMethod().toUpperCase();
		RequestMappingInfo info = new RequestMappingInfo();
		String servletPath=req.getServletPath();
		if (servletPath.length()>0) {
			info.setPath(servletPath.substring(1));
		}else{
			info.setPath("");
		}
		System.out.println(info);
		switch (requestMethod.toUpperCase()) {
		case "GET":
			info.setMethod(RequestMethodType.GET);
			break;
		case "POST":
			info.setMethod(RequestMethodType.POST);
			break;
		case "PUT":
			info.setMethod(RequestMethodType.PUT);
			break;
		case "DELETE":
			info.setMethod(RequestMethodType.DELETE);
			break;
		default:
			break;
		}
		return info;
	}
	
}
