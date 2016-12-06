package com.hectorlopezfernandez.blog.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value="/login.page")
	public String login(ModelMap model) {
		logger.debug("Going into LoginController.login()");
		return "admin/login";
	}

}