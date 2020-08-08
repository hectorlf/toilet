package com.hectorlopezfernandez.blog.setup;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitController {

	private static final Logger logger = LoggerFactory.getLogger(InitController.class);

	private final InitService initService;

	@Inject
	public InitController(InitService initService) {
		this.initService = initService;
	}

	@RequestMapping(value="/initialize")
	public String initialize(ModelMap model) {
		logger.debug("Going into InitController.initialize()");
		initService.initialize();
		return "redirect:/index.page";
	}

}