package com.hectorlopezfernandez.blog.index;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hectorlopezfernandez.blog.metadata.MetadataService;
import com.hectorlopezfernandez.blog.metadata.Preferences;
import com.hectorlopezfernandez.blog.post.ArchiveService;
import com.hectorlopezfernandez.blog.post.Post;

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

}
