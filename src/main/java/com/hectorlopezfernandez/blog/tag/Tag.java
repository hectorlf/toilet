package com.hectorlopezfernandez.blog.tag;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Tags are simple text labels that can be attached to Posts and Pages
 * to categorize and allow searching for topics
 * 
 * Implementation notes:
 * - slug stores the URL-friendly version of the name, which can
 *   appear in some parts of the UI, e.g. /tags/{name}
 * 
 * @author hector
 */
@Document(collection="tags")
public class Tag {

	@Id
	private String id;
	private String slug;
	private String name;
	private int count;
	
	// constructors
	
	public Tag() {
	}
	
	public Tag(String slug, String name) {
		this.slug = slug;
		this.name = name;
	}	

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

	// equals & hashcode

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Tag)) return false;
		return this.slug != null && this.slug.equals(((Tag)other).slug);
	}

	@Override
	public int hashCode() {
		return slug == null ? 0 : slug.hashCode();
	}

}
