package jspring.web.servlet.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jspring.web.servlet.Model;

public interface View {
	
	
	public void render(Model model, HttpServletRequest request, HttpServletResponse resp);
	
	
}
