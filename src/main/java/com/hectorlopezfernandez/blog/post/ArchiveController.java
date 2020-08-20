package com.hectorlopezfernandez.blog.post;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hectorlopezfernandez.blog.metadata.MetadataService;
import com.hectorlopezfernandez.blog.metadata.Preferences;

@Controller
@RequestMapping(value="/archive")
public class ArchiveController {

	private static final Logger logger = LoggerFactory.getLogger(ArchiveController.class);

	private final MetadataService metadataService;
	private final ArchiveService archiveService;

	@Inject
	public ArchiveController(MetadataService metadataService, ArchiveService archiveService) {
		this.metadataService = metadataService;
		this.archiveService = archiveService;
	}

	@RequestMapping
	public String archiveRoot(ModelMap model) {
		logger.debug("Going into ArchiveController.root()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<ArchiveEntry> entries = archiveService.listArchiveEntries();
		model.addAttribute("entries", entries);
		return "web/pages/archive-entries";
	}

	@RequestMapping("/{year}")
	public String yearly(@PathVariable("year") String year, ModelMap model) {
		logger.debug("Going into ArchiveController.yearly()");
		logger.debug("year " + year);
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
//		List<Post> posts = archiveService.listIndexPosts();
//		model.addAttribute("posts", posts);
		return "web/pages/archive-entries";
	}

	@RequestMapping("/{year}/{month}")
	public String monthly(@PathVariable("year") String year, @PathVariable("month") String month, ModelMap model) {
		logger.debug("Going into ArchiveController.monthly()");
		logger.debug("year " + year);
		logger.debug("month " + month);
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
//		List<Post> posts = archiveService.listIndexPosts();
//		model.addAttribute("posts", posts);
		return "web/pages/posts-for-archive-entry";
	}

	@RequestMapping("/{year}/{month}/{post}")
	public String permalink(@PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("post") String post, ModelMap model) {
		logger.debug("Going into ArchiveController.permalink()");
		logger.debug("year " + year);
		logger.debug("month " + month);
		logger.debug("post " + post);
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
//		List<Post> posts = archiveService.listIndexPosts();
//		model.addAttribute("posts", posts);
		return "web/pages/post";
	}

}