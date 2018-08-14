package jspring.web.servlet;

public class ModelAndView {

	private Model model;
	private String viewName;
	
	public ModelAndView(Model model,String viewName){
		this.model=model;
		this.viewName=viewName;
	}

	public Model getModel() {
		return model;
	}

	public String getViewName() {
		return viewName;
	}
	
	
}
