package com.hectorlopezfernandez.toilet.page;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * View object specific to the sitemap
 * 
 * @author hector
 */
public class SitemapPageView {

	private String slug;
	private long lastModificationTime;

	public SitemapPageView(String slug, long lastModificationTime) {
		this.slug = slug;
		this.lastModificationTime = lastModificationTime;
	}

	// utility getters

	public LocalDateTime getLastModificationTimeAsDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModificationTime), ZoneId.systemDefault());
	}

	// getters & setters

	public String getSlug() {
		return slug;
	}

	public long getLastModificationTime() {
		return lastModificationTime;
	}

}
