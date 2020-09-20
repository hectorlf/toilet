package com.hectorlopezfernandez.blog.post;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hectorlopezfernandez.blog.ContentNotFoundException;
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
		logger.debug("Going into .root()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<ArchiveEntry> entries = archiveService.listArchiveEntries();
		model.addAttribute("entries", entries);
		return "web/pages/archive-entries";
	}

	@RequestMapping("/{year}")
	public String yearly(@PathVariable("year") Integer year, ModelMap model) {
		logger.debug("Going into .yearly(), with year {}", year);
		model.addAttribute("searchDate", year.toString());
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Post> posts = archiveService.listPostsByYear(year);
		model.addAttribute("posts", posts);
		return "web/pages/posts-for-archive-entry";
	}

	@RequestMapping("/{year}/{month}")
	public String monthly(@PathVariable("year") Integer year, @PathVariable("month") Integer month, ModelMap model) {
		logger.debug("Going into .monthly(), with year: {}, and month: {}", year, month);
		//FIXME
		model.addAttribute("searchDate", year.toString() + "-" + month.toString());
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Post> posts = archiveService.listPostsByMonth(year, month);
		model.addAttribute("posts", posts);
		return "web/pages/posts-for-archive-entry";
	}

	@RequestMapping("/{year}/{month}/{slug}")
	public String permalink(@PathVariable("year") Integer year, @PathVariable("month") Integer month, @PathVariable("slug") String slug, ModelMap model) {
		logger.debug("Going into .permalink(), with year: {}, month: {}, and slug: {}", year, month, slug);
		Optional<SinglePostView> postData = archiveService.getDataForPostPage(year, month, slug);
		if (postData.isEmpty()) {
			throw new ContentNotFoundException("No post exists with year: " + year + ", month: " + month + ", and slug: " + slug);
		}
		model.addAttribute("post", postData.get().getPost());
		model.addAttribute("author", postData.get().getAuthor());
		model.addAttribute("tags", postData.get().getTags());
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		return "web/pages/post";
	}

}
