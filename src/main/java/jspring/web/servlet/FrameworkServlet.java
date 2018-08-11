package jspring.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jspring.web.servlet.config.annotation.RequestMethodType;
import jspring.web.servlet.context.ApplicationContext;

/**
 * 
 * @author wills
 *
 */
public abstract class FrameworkServlet extends HttpServlet {

	private ApplicationContext applicationContext;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	@Override
	protected void doTrace(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			doService(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected RequestInfo parseRequestInfo(HttpServletRequest req) {
		String requestMethod = req.getMethod();
		RequestInfo info = new RequestInfo();
		info.setPath(req.getServletPath().substring(1));

		switch (requestMethod) {
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

	protected abstract void doService(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
