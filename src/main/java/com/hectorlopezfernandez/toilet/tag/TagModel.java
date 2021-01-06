package com.hectorlopezfernandez.toilet.tag;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * View of a Tag as seen by the admin API
 * 
 * @author hector
 * @see Tag
 */
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagModel extends RepresentationModel<TagModel> {

	private String id;
	@NonNull
	private String slug;
	@NonNull
	private String name;
	private int count;

	// getters & setters

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	// equals and hashcode

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj) return true;
		if (this.slug == null) return false;
		if (obj == null || !obj.getClass().equals(this.getClass())) return false;
		TagModel that = (TagModel) obj;
		return this.slug.equals(that.slug);
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (slug != null) result += 31 * slug.hashCode();
		return result;
	}

}
