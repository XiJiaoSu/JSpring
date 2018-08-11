package jspring.home.controller;

import jspring.web.servlet.config.annotation.Controller;
import jspring.web.servlet.config.annotation.RequestMapping;
import jspring.web.servlet.config.annotation.RequestMethodType;

@Controller
// @RequestMapping("users")
public class UserController {

	@RequestMapping(value = "user")
	public void test() {
		System.out.println("test");
	}

	@RequestMapping(value = "user", method = RequestMethodType.POST)
	public void test2() {
		System.out.println("test2");
	}

	public void test3() {
	}

}
