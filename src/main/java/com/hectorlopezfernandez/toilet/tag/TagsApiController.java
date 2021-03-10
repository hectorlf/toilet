package com.hectorlopezfernandez.toilet.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hectorlopezfernandez.toilet.DocumentNotFoundException;

/**
 * Deals with the Tag resource, i.e. the /admin/api/tags endpoints
 * 
 * @author hector
 */
@Controller
@RequestMapping(path = "/admin/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Tag.class)
public class TagsApiController {

	private static final Logger logger = LoggerFactory.getLogger(TagsApiController.class);

	private final TagService tagService;
	private final TagModelAssembler tagModelAssembler;
	private final EntityLinks entityLinks;

	@Inject
	public TagsApiController(TagService tagService, TagModelAssembler tagModelAssembler, EntityLinks entityLinks) {
		this.tagService = tagService;
		this.tagModelAssembler = tagModelAssembler;
		this.entityLinks = entityLinks;
	}

	// FIXME make "slug" a constant somewhere
	@GetMapping
	@ResponseBody
	public CollectionModel<TagModel> tags(@RequestParam(name = "slug", required = false) String slug) {
		logger.debug("Going into .tags()");
		Map<String,String> filters = new HashMap<>();
		if (slug != null) filters.put("slug", slug);
		List<Tag> tags = tagService.listTags(filters);
		return tagModelAssembler.toCollectionModel(tags);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TagModel> tag(@PathVariable("id") String id) {
		logger.debug("Going into .tag()");
		Optional<Tag> tag = tagService.getTag(id);
		ResponseEntity<TagModel> response;
		if (tag.isEmpty()) {
			response = ResponseEntity.notFound().build();
		} else {
			response = ResponseEntity.ok(tagModelAssembler.toModel(tag.get()));
		}
		return response;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TagModel> createTag(@RequestBody @Validated TagModel model) {
		logger.debug("Going into .createTag()");
		// prevent updates through this endpoint
		if (model.getId() != null && !model.getId().isEmpty()) return ResponseEntity.badRequest().build();

		Tag createdTag = tagService.addTag(tagModelAssembler.toEntity(model));
		return ResponseEntity.created(entityLinks.linkToItemResource(createdTag.getClass(), createdTag.getId()).toUri())
				.body(tagModelAssembler.toModel(createdTag));
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TagModel> updateTag(@PathVariable("id") String id, @RequestBody @NonNull @Validated TagModel model) {
		logger.debug("Going into .updateTag()");
		model.setId(id);
		ResponseEntity<TagModel> response;
		try {
			Tag updatedTag = tagService.editTag(tagModelAssembler.toEntity(model));
			response = ResponseEntity
					.created(entityLinks.linkToItemResource(updatedTag.getClass(), updatedTag.getId()).toUri())
					.body(tagModelAssembler.toModel(updatedTag));
		} catch (DocumentNotFoundException dnfe) {
			// while not the most correct thing to do, our api contract expects a valid id in the request
			response = ResponseEntity.notFound().build();
		}
		return response;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTag(@PathVariable("id") String id) {
		logger.debug("Going into .deleteTag()");
		tagService.deleteTag(id);
		return ResponseEntity.noContent().build();
	}

}
