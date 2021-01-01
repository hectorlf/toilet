package com.hectorlopezfernandez.toilet.tag;

/**
 * Transforms from and to Tag
 * 
 * @author hector
 * @see Tag
 */
class TagMapper {

	private TagMapper() {
		// non-instantiable
	}

	static Tag from(TagView view) {
		Tag tag = new Tag();
		tag.setId(view.getId());
		tag.setName(view.getName());
		tag.setSlug(view.getSlug());
		return tag;
	}

	static TagView from(Tag tag) {
		TagView view = new TagView();
		view.setCount(tag.getCount());
		view.setId(tag.getId());
		view.setName(tag.getName());
		view.setSlug(tag.getSlug());
		return view;
	}

}
