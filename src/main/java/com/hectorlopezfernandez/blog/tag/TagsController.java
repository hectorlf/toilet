package com.hectorlopezfernandez.blog.tag;

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
import com.hectorlopezfernandez.blog.post.ArchiveService;
import com.hectorlopezfernandez.blog.post.Post;

@Controller
@RequestMapping(value="/tags")
public class TagsController {

	private static final Logger logger = LoggerFactory.getLogger(TagsController.class);

	@Inject
	private MetadataService metadataService;
	@Inject
	private TagService tagService;
	@Inject
	private ArchiveService archiveService;

	@RequestMapping
	public String root(ModelMap model) {
		logger.debug("Going into TagsController.root()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Tag> tags = tagService.listTags();
		model.addAttribute("tags", tags);
		return "web/tag-list";
	}

	@RequestMapping("/{tag}")
	public String byTag(@PathVariable("tag") String slug, ModelMap model) {
		logger.debug("Going into TagsController.byTag()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Post> tagPosts = archiveService.listPostsByTag(slug);
		model.addAttribute("posts", tagPosts);
		return "web/tag-posts-list";
	}

	public void setMetadataService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}

	public void setArchiveService(ArchiveService archiveService) {
		this.archiveService = archiveService;
	}

}
