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
import com.hectorlopezfernandez.blog.post.ArchiveEntry.YearlyEntry;

@Controller
@RequestMapping(value="/archive")
public class ArchiveController {

	private static final Logger logger = LoggerFactory.getLogger(ArchiveController.class);

	@Inject
	private MetadataService metadataService;
	@Inject
	private ArchiveService archiveService;

	@RequestMapping
	public String archiveRoot(ModelMap model) {
		logger.debug("Going into ArchiveController.root()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<YearlyEntry> years = archiveService.listYearsWithPublications();
		model.addAttribute("years", years);
		return "web/year-entry-list";
	}

	@RequestMapping("/{year}")
	public String yearly(@PathVariable("year") String year, ModelMap model) {
		logger.debug("Going into ArchiveController.yearly()");
		logger.debug("year " + year);
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
//		List<Post> posts = archiveService.listIndexPosts();
//		model.addAttribute("posts", posts);
		return "web/archive-entry-list";
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
		return "web/post-list";
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
		return "web/post";
	}

	public void setMetadataService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

	public void setArchiveService(ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

}