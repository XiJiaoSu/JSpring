package jspring.web.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jspring.web.servlet.HandlerAdapter;
import jspring.web.servlet.Model;
import jspring.web.servlet.ModelAndView;

public class SimpleControllerHanderAdapter implements HandlerAdapter{


	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		return handleInternal(request, response, (HandlerMethod)handler);
	}
	
	
	protected  ModelAndView handleInternal(HttpServletRequest request,
			HttpServletResponse response, HandlerMethod handlerMethod) throws Exception{
		handlerMethod.getMethod().invoke(handlerMethod.getObject(), null);
		Model model=new Model();
		String viewName="user";
		ModelAndView mv=new ModelAndView(model, viewName);
		return mv;
	}
	

}
