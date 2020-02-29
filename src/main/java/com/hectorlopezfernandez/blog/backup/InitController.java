package com.hectorlopezfernandez.blog.backup;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InitController {

	private static final Logger logger = LoggerFactory.getLogger(InitController.class);

	@Inject
	private BackupService backupService;

	@RequestMapping(value="/initialize")
	public String initialize(ModelMap model) {
		logger.debug("Going into InitController.initialize()");
		backupService.initialize();
		return "redirect:/index.page";
	}

	public void setBackupService(BackupService backupService) {
		this.backupService = backupService;
	}

}