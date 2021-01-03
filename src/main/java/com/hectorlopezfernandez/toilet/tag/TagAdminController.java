package com.hectorlopezfernandez.toilet.tag;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class TagAdminController {

	private static final Logger logger = LoggerFactory.getLogger(TagAdminController.class);

	private final TagService tagService;

	@Inject
	public TagAdminController(TagService tagService) {
		this.tagService = tagService;
	}

	@RequestMapping("/admin/tags.page")
	public String tagsPage() {
		logger.debug("Going into Tag's AdminController.tagsPage()");
		return "admin/pages/tags";
	}

	@RequestMapping("/admin/tag-new.page")
	public String newTagPage() {
		logger.debug("Going into Tag's AdminController.newTagPage()");
		return "admin/pages/tag-new";
	}

	@RequestMapping("/admin/tag-edit.page")
	public String editTagPage(@RequestParam("id") String id, ModelMap model) {
		logger.debug("Going into Tag's AdminController.editTagPage()");
		model.put("tagId", id);
		return "admin/pages/tag-edit";
	}

	@GetMapping(path = "/admin/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public TagViewContainer listTags(@RequestParam(name = "slug", required = false) Optional<String> slug) {
		logger.debug("Going into Tag's AdminController.listTags()");
		List<TagView> tags = tagService.listTags(slug).stream().map(tag -> TagMapper.from(tag))
				.collect(Collectors.toList());
		return new TagViewContainer(tags, tags.size());
	}

	@RequestMapping(path = "/admin/api/tags", method = POST)
	public ResponseEntity<TagView> postTag(@RequestBody @Validated TagView tag) {
		logger.debug("Going into Tag's AdminController.postTag()");
		if (tag.getId() != null && !tag.getId().isEmpty()) return ResponseEntity.badRequest().build();

		Tag createdTag = tagService.addTag(new Tag(tag.getSlug(), tag.getName()));
		return ResponseEntity
				.created(UriComponentsBuilder.fromPath("/admin/api/tags/{tagId}").build(createdTag.getId()))
				.body(TagMapper.from(createdTag));
	}

	@RequestMapping(path = "/admin/api/tags/{tagId}", method = GET)
	public ResponseEntity<TagView> getTag(@PathVariable("tagId") String tagId) {
		logger.debug("Going into Tag's AdminController.getTag()");
		Optional<Tag> tag = tagService.getTag(tagId);
		return tag.isPresent() ? ResponseEntity.ok(TagMapper.from(tag.get())) : ResponseEntity.notFound().build();
	}

	@RequestMapping(path = "/admin/api/tags/{tagId}", method = PUT)
	public ResponseEntity<TagView> putTag(@PathVariable("tagId") String tagId, @RequestBody @Validated TagView tag) {
		logger.debug("Going into Tag's AdminController.putTag()");
		tag.setId(tagId);
		tagService.editTag(TagMapper.from(tag));
		return getTag(tagId);
	}

	@RequestMapping(path = "/admin/api/tags/{tagId}", method = DELETE)
	public ResponseEntity<Void> deleteTag(@PathVariable("tagId") String tagId) {
		logger.debug("Going into Tag's AdminController.deleteTag()");
		tagService.deleteTag(tagId);
		return ResponseEntity.noContent().build();
	}

}
