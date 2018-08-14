package jspring.home.controller;

import jspring.web.servlet.config.annotation.Controller;
import jspring.web.servlet.config.annotation.RequestMapping;
import jspring.web.servlet.config.annotation.RequestMethodType;

@Controller
public class DefaultController {

	@RequestMapping(value="",method=RequestMethodType.GET)
	public void defaults(){
		System.out.println("default");
	}
	
}
