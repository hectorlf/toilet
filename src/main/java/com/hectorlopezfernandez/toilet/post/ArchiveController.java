package com.hectorlopezfernandez.toilet.post;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hectorlopezfernandez.toilet.ContentNotFoundException;
import com.hectorlopezfernandez.toilet.metadata.MetadataService;
import com.hectorlopezfernandez.toilet.metadata.Preferences;

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
	public String yearly(@PathVariable("year") String year, ModelMap model) {
		logger.debug("Going into .yearly(), with year {}", year);
		// handle parsing manually to return 404 instead of 400
		int parsedYear = 0;
		try {
			parsedYear = Integer.parseInt(year);
		} catch (NumberFormatException nfe) {
			throw new ContentNotFoundException("Tried to access the yearly archive with an invalid year: " + year);
		}

		model.addAttribute("searchDate", year);
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Post> posts = archiveService.listPostsByYear(parsedYear);
		model.addAttribute("posts", posts);
		return "web/pages/posts-for-archive-entry";
	}

	@RequestMapping("/{year}/{month}")
	public String monthly(@PathVariable("year") String year, @PathVariable("month") String month, ModelMap model) {
		logger.debug("Going into .monthly(), with year: {}, and month: {}", year, month);
		// handle parsing manually to return 404 instead of 400
		int parsedYear = 0;
		int parsedMonth = 0;
		try {
			parsedYear = Integer.parseInt(year);
			parsedMonth = Integer.parseInt(month);
		} catch (NumberFormatException nfe) {
			throw new ContentNotFoundException("Tried to access the monthly archive with an invalid year: " + year + " or month: " + month);
		}

		//FIXME
		model.addAttribute("searchDate", year + "-" + month);
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Post> posts = archiveService.listPostsByMonth(parsedYear, parsedMonth);
		model.addAttribute("posts", posts);
		return "web/pages/posts-for-archive-entry";
	}

	@RequestMapping("/{year}/{month}/{slug}")
	public String permalink(@PathVariable("year") String year, @PathVariable("month") String month, @PathVariable("slug") String slug, ModelMap model) {
		logger.debug("Going into .permalink(), with year: {}, month: {}, and slug: {}", year, month, slug);
		// handle parsing manually to return 404 instead of 400
		int parsedYear = 0;
		int parsedMonth = 0;
		try {
			parsedYear = Integer.parseInt(year);
			parsedMonth = Integer.parseInt(month);
		} catch (NumberFormatException nfe) {
			throw new ContentNotFoundException("Tried to access a permalink with an invalid year: " + year + " or month: " + month);
		}

		Optional<SinglePostView> postData = archiveService.getDataForPostPage(parsedYear, parsedMonth, slug);
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
