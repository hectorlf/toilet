package com.hectorlopezfernandez.blog.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping(value="/index.page")
	public String welcome(ModelMap model) {
		logger.debug("Going into IndexController.welcome()");
		return "index";
	}

}
