package com.hectorlopezfernandez.blog.metadata;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="preferences")
public class Preferences {

	// Only allow one preferences object (one blog)
	public static final String ID = "1";

	@Id
	private String id = ID;
	private String title;
	private String tagline;
	private Boolean paginateIndexPage;
	private Integer postsPerIndexPage;
	private Integer maxPostAgeInDaysForFeeds;
	private String defaultLanguage;

	// getters & setters
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTagline() {
		return tagline;
	}
	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public Boolean getPaginateIndexPage() {
		return paginateIndexPage;
	}
	public void setPaginateIndexPage(Boolean paginateIndexPage) {
		this.paginateIndexPage = paginateIndexPage;
	}

	public Integer getPostsPerIndexPage() {
		return postsPerIndexPage;
	}
	public void setPostsPerIndexPage(Integer postsPerIndexPage) {
		this.postsPerIndexPage = postsPerIndexPage;
	}

	public Integer getMaxPostAgeInDaysForFeeds() {
		return maxPostAgeInDaysForFeeds;
	}
	public void setMaxPostAgeInDaysForFeeds(Integer maxPostAgeInDaysForFeeds) {
		this.maxPostAgeInDaysForFeeds = maxPostAgeInDaysForFeeds;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}
	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

}