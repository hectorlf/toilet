package com.hectorlopezfernandez.toilet.tag;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

/**
 * Transforms from and to Tag
 * 
 * @author hector
 * @see Tag
 */
public class TagModelAssembler extends RepresentationModelAssemblerSupport<Tag, TagModel> {

	public TagModelAssembler() {
		super(TagsApiController.class, TagModel.class);
	}

	@Override
	public TagModel toModel(Tag entity) {
		if (entity == null) throw new IllegalArgumentException("The entity parameter cannot be null");

		TagModel model = createModelWithId(entity.getId(), entity);
		model.setCount(entity.getCount());
		model.setId(entity.getId());
		model.setName(entity.getName());
		model.setSlug(entity.getSlug());
		return model;
	}

	public Tag toEntity(TagModel model) {
		if (model == null) throw new IllegalArgumentException("The model parameter cannot be null");
		Tag entity = new Tag();
		entity.setId(model.getId());
		entity.setName(model.getName());
		entity.setSlug(model.getSlug());
		return entity;
	}

	/*
	 * Override to avoid reflection call
	 */
	@Override
	protected TagModel instantiateModel(Tag entity) {
		return new TagModel();
	}

}
