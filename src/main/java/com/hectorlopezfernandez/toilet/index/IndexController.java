package com.hectorlopezfernandez.toilet.index;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hectorlopezfernandez.toilet.metadata.MetadataService;
import com.hectorlopezfernandez.toilet.metadata.Preferences;
import com.hectorlopezfernandez.toilet.post.ArchiveService;
import com.hectorlopezfernandez.toilet.post.Post;

@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	private final MetadataService metadataService;
	private final ArchiveService archiveService;

	@Inject
	public IndexController(MetadataService metadataService, ArchiveService archiveService) {
		this.archiveService = archiveService;
		this.metadataService = metadataService;
	}

	// web

	@RequestMapping
	public String root() {
		logger.debug("Going into IndexController.root()");
		return "forward:/index.page";
	}

	@RequestMapping(value="/index.page")
	public String welcome(ModelMap model) {
		logger.debug("Going into IndexController.welcome()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Post> posts = archiveService.listIndexPosts();
		model.addAttribute("posts", posts);
		return "web/pages/index";
	}

	// admin console

	@RequestMapping("/admin/index.page")
	public String dashboard(ModelMap model) {
		logger.debug("Going into .dashboard()");
		return "admin/pages/dashboard";
	}

}
