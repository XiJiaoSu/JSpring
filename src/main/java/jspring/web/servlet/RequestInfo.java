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
		RequestInfo other = (RequestInfo) obj;
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

	@Override
	public String toString() {
		return "RequestInfo [path=" + path + ", method=" + method + "]";
	}
	
	
	
}
