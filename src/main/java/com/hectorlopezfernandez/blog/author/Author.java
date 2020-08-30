package com.hectorlopezfernandez.blog.author;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * An Author is the assigned face of a piece of content (e.g. a post)
 */
@Document(collection="authors")
public class Author {

	@Id
	private String id;
	@Indexed(unique=true)
	private String slug;
	private String displayName;
	private String about;
	private String relatedUrl;

	// getters & setters
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	public String getRelatedUrl() {
		return relatedUrl;
	}
	public void setRelatedUrl(String relatedUrl) {
		this.relatedUrl = relatedUrl;
	}

	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}

}
