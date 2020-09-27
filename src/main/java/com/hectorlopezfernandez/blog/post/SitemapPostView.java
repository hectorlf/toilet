package com.hectorlopezfernandez.blog.post;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * View object specific to the sitemap
 * 
 * @author hector
 */
public class SitemapPostView {

	private String slug;
	private long publicationTime;
	private long lastModificationTime;

	public SitemapPostView(String slug, long publicationTime, long lastModificationTime) {
		this.slug = slug;
		this.lastModificationTime = lastModificationTime;
		this.publicationTime = publicationTime;
	}

	// utility getters

	public LocalDateTime getPublicationTimeAsDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(publicationTime), ZoneId.systemDefault());
	}

	public LocalDateTime getLastModificationTimeAsDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModificationTime), ZoneId.systemDefault());
	}

	// getters & setters

	public String getSlug() {
		return slug;
	}

	public long getPublicationTime() {
		return publicationTime;
	}

	public long getLastModificationTime() {
		return lastModificationTime;
	}

}
