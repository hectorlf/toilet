package com.hectorlopezfernandez.blog.tag;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.hectorlopezfernandez.blog.metadata.MetadataService;
import com.hectorlopezfernandez.blog.metadata.Preferences;
import com.hectorlopezfernandez.blog.post.ArchiveService;
import com.hectorlopezfernandez.blog.post.Post;

//FIXME split this class into two again
@Controller
public class TagsController {

	private static final Logger logger = LoggerFactory.getLogger(TagsController.class);

	private final MetadataService metadataService;
	private final TagsService tagsService;
	private final ArchiveService archiveService;
	private final TagService tagService;

	@Inject
	public TagsController(TagsService tagsService, MetadataService metadataService, ArchiveService archiveService,
			TagService tagService) {
		this.tagsService = tagsService;
		this.metadataService = metadataService;
		this.archiveService = archiveService;
		this.tagService = tagService;
	}

	// web
	
	@RequestMapping("/tags")
	public String root(ModelMap model) {
		logger.debug("Going into TagsController.root()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Tag> tags = tagsService.listTags();
		model.addAttribute("tags", tags);
		return "web/pages/tag-list";
	}

	@RequestMapping("/tags/{tag}")
	public String byTag(@PathVariable("tag") String slug, ModelMap model) {
		logger.debug("Going into TagsController.byTag()");
		Preferences prefs = metadataService.getPreferences();
		model.addAttribute("preferences", prefs);
		List<Post> tagPosts = archiveService.listPostsByTag(slug);
		model.addAttribute("posts", tagPosts);
		return "web/pages/tag-posts-list";
	}

	// admin

	@RequestMapping("/admin/tags.page")
	public String tags() {
		logger.debug("Going into Tag's AdminController.tags()");
		return "admin/pages/tags";
	}

	@RequestMapping("/admin/tag-new.page")
	public String newTag() {
		logger.debug("Going into Tag's AdminController.editTag()");
		return "admin/pages/tag-new";
	}

	@RequestMapping("/admin/tag-edit.page")
	public String editTag(@RequestParam("id") String id, ModelMap model) {
		logger.debug("Going into Tag's AdminController.editTag()");
		model.put("tagId", id);
		return "admin/pages/tag-edit";
	}

	// FIXME should use a TagViewContainer instead of the model object
	@RequestMapping(path = "/admin/api/tags", method = GET)
	@ResponseBody
	public TagContainer listTags() {
		logger.debug("Going into Tag's AdminController.listTags()");
		List<Tag> tags = tagService.listTags();
		return new TagContainer(tags, tags.size());
	}

	// FIXME should use a TagView instead of the model object
	@RequestMapping(path = "/admin/api/tags", method = POST)
	public ResponseEntity<Tag> postTag(@RequestBody Tag tag) {
		logger.debug("Going into Tag's AdminController.postTag()");
		Tag createdTag = tagService.addTag(tag);
		return ResponseEntity.created(UriComponentsBuilder.fromPath("/admin/api/tags/{tagId}").build(createdTag.getId())).build();
	}

	// FIXME should use a TagView instead of the model object
	@RequestMapping(path = "/admin/api/tags/{tagId}", method = GET)
	public ResponseEntity<Tag> getTag(@PathVariable("tagId") String tagId) {
		logger.debug("Going into Tag's AdminController.getTag()");
		Optional<Tag> tag = tagService.getTag(tagId);
		return tag.isPresent() ? ResponseEntity.ok(tag.get()) : ResponseEntity.notFound().build();
	}

	// FIXME should use a TagView instead of the model object
	@RequestMapping(path = "/admin/api/tags/{tagId}", method = PUT)
	public ResponseEntity<Tag> putTag(@PathVariable("tagId") String tagId) {
		logger.debug("Going into Tag's AdminController.putTag()");
		//tagService.editTag(tagId);
		return getTag(tagId);
	}

	@RequestMapping(path = "/admin/api/tags/{tagId}", method = DELETE)
	public ResponseEntity<Void> deleteTag(@PathVariable("tagId") String tagId) {
		logger.debug("Going into Tag's AdminController.deleteTag()");
		//tagService.deleteTag(tagId);
		return ResponseEntity.noContent().build();
	}

}
