package com.hectorlopezfernandez.toilet.feed;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hectorlopezfernandez.toilet.metadata.MetadataService;
import com.hectorlopezfernandez.toilet.metadata.Preferences;
import com.hectorlopezfernandez.toilet.post.ArchiveService;
import com.hectorlopezfernandez.toilet.post.FeedPostsView;

@Controller
public class FeedController {

	private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

	private final MetadataService metadataService;
	private final ArchiveService archiveService;

	@Inject
	public FeedController(MetadataService metadataService, ArchiveService archiveService) {
		this.metadataService = metadataService;
		this.archiveService = archiveService;
	}

	@RequestMapping(value="/feed.atom", method = RequestMethod.GET, produces = MediaType.APPLICATION_ATOM_XML_VALUE)
	public String feed(ModelMap model, HttpServletResponse response) {
		logger.debug("Going into .feed()");
		Preferences preferences = metadataService.getPreferences();
		model.addAttribute("preferences", preferences);
		FeedPostsView postsView = archiveService.listPostsForFeed();
		model.addAttribute("lastUpdatedOnDate", postsView.getLastUpdatedOnDate());
		model.addAttribute("posts", postsView.getPosts());
		// required to override Pebble's default content type
		response.setContentType(MediaType.APPLICATION_ATOM_XML_VALUE);
		return "web/pages/feed";
	}

}
