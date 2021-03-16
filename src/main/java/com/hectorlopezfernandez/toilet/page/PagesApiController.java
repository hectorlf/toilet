package com.hectorlopezfernandez.toilet.page;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Deals with the Page resource, i.e. the /admin/api/pages endpoints
 * 
 * @author hector
 */
@Controller
@RequestMapping(path = "/admin/api/pages", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Page.class)
public class PagesApiController {

	private static final Logger logger = LoggerFactory.getLogger(PagesApiController.class);

	private final PageService pageService;
	private final PageModelAssembler pageModelAssembler;
	private final EntityLinks entityLinks;

	@Inject
	public PagesApiController(PageService pageService, PageModelAssembler pageModelAssembler, EntityLinks entityLinks) {
		this.pageService = pageService;
		this.pageModelAssembler = pageModelAssembler;
		this.entityLinks = entityLinks;
	}

	// FIXME make "slug" a constant somewhere
	@GetMapping
	@ResponseBody
	public CollectionModel<PageModel> pages(@RequestParam(name = "slug", required = false) String slug) {
		logger.debug("Going into .pages()");
		Map<String,String> filters = new HashMap<>();
		if (slug != null) filters.put("slug", slug);
		List<Page> pages = pageService.listPages(filters);
		return pageModelAssembler.toCollectionModel(pages);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PageModel> page(@PathVariable("id") String id) {
		logger.debug("Going into .page()");
		Optional<Page> page = pageService.getPage(id);
		ResponseEntity<PageModel> response;
		if (page.isEmpty()) {
			response = ResponseEntity.notFound().build();
		} else {
			response = ResponseEntity.ok(pageModelAssembler.toModel(page.get()));
		}
		return response;
	}
/*
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
*/
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePage(@PathVariable("id") String id) {
		logger.debug("Going into .deletePage()");
		pageService.deletePage(id);
		return ResponseEntity.noContent().build();
	}

}
