package com.hectorlopezfernandez.toilet.tag;

import org.springframework.lang.NonNull;

/**
 * View of a Tag as seen by the admin API
 * 
 * @author hector
 * @see Tag
 */
public class TagView {

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
	
}
