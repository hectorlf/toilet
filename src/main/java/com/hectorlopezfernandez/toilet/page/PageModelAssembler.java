package com.hectorlopezfernandez.toilet.page;

import java.time.ZoneId;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

/**
 * Transforms from and to Page
 * 
 * @author hector
 * @see Page
 */
public class PageModelAssembler extends RepresentationModelAssemblerSupport<Page, PageModel> {

	public PageModelAssembler() {
		super(PagesApiController.class, PageModel.class);
	}

	@Override
	public PageModel toModel(Page entity) {
		if (entity == null) throw new IllegalArgumentException("The entity parameter cannot be null");

		PageModel model = createModelWithId(entity.getId(), entity);
		model.setContent(entity.getContent());
		model.setId(entity.getId());
		model.setMetaDescription(entity.getMetaDescription());
		model.setPublicationTime(entity.getPublicationTimeAsDate());
		model.setPublished(entity.isPublished());
		model.setSlug(entity.getSlug());
		model.setTitle(entity.getTitle());
		return model;
	}

	public Page toEntity(PageModel model) {
		if (model == null) throw new IllegalArgumentException("The model parameter cannot be null");
		Page entity = new Page();
		entity.setContent(model.getContent());
		entity.setId(model.getId());
		entity.setMetaDescription(model.getMetaDescription());
		entity.setPublicationTime(model.getPublicationTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		entity.setPublished(model.isPublished());
		entity.setSlug(model.getSlug());
		entity.setTitle(model.getTitle());
		return entity;
	}

	/*
	 * Override to avoid reflection call
	 */
	@Override
	protected PageModel instantiateModel(Page entity) {
		return new PageModel();
	}

}
