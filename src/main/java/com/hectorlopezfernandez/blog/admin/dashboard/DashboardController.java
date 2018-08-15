package com.hectorlopezfernandez.blog.admin.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@RequestMapping("/dashboard.page")
	public String dashboard(ModelMap model) {
		logger.debug("Going into DashboardController.dashboard()");
		return "admin/dashboard";
	}

}