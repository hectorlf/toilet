package com.hectorlopezfernandez.blog.tag;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hectorlopezfernandez.blog.metadata.MetadataService;
import com.hectorlopezfernandez.blog.metadata.Preferences;
import com.hectorlopezfernandez.blog.post.ArchiveService;
import com.hectorlopezfernandez.blog.post.Post;

@Controller
public class TagsController {

	private static final Logger logger = LoggerFactory.getLogger(TagsController.class);

	private final MetadataService metadataService;
	private final TagsService tagsService;
	private final ArchiveService archiveService;

	@Inject
	public TagsController(TagsService tagsService, MetadataService metadataService, ArchiveService archiveService) {
		this.tagsService = tagsService;
		this.metadataService = metadataService;
		this.archiveService = archiveService;
	}

	@RequestMapping("/tags")
	public String root(ModelMap model) {
		logger.debug("Going into .root()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Tag> tags = tagsService.listTags();
		model.addAttribute("tags", tags);
		return "web/pages/tag-list";
	}

	@RequestMapping("/tags/{tag}")
	public String byTag(@PathVariable("tag") String slug, ModelMap model) {
		logger.debug("Going into .byTag()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		Optional<Tag> tag = tagsService.getTagBySlug(slug);
		List<Post> tagPosts = tag.isPresent() ? archiveService.listPostsByTag(tag.get().getId()) : Collections.emptyList();
		model.addAttribute("posts", tagPosts);
		return "web/pages/posts-for-tag";
	}

}
