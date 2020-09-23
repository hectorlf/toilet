package com.hectorlopezfernandez.blog.page;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Page contains data about a single static web page,
 * that can be linked and shown as "everything else" that's
 * not a blog post.
 * 
 * Implementation notes:
 * - slug stores the URL path segments, after /pages, that uniquely identify the page
 *   and has to conform to URL syntax
 * - metaDescription stores a meta that can be crawled by search engines, and has
 *   to be properly HTML encoded
 * - title contains a text that will be used in the HTML head and optionally in
 *   other places of a layout, and has to be properly HTML encoded
 * - content is the proper page HTML body, including markup (relative to the layout
 *   container)
 * 
 * Restrictions: slug is unique in the DB
 * 
 * @author hector
 */
@Document(collection="pages")
public class Page {

	@Id
	private String id;
	private String slug;

	private String metaDescription;
	private String title;
	private String content;

	private long creationTime;
	private long publicationTime;
	private long lastModificationTime;
	private boolean published;

	// utility getters

	public LocalDateTime getCreationTimeAsDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(creationTime), ZoneId.systemDefault());
	}

	public LocalDateTime getPublicationTimeAsDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(publicationTime), ZoneId.systemDefault());
	}

	public LocalDateTime getLastModificationTimeAsDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModificationTime), ZoneId.systemDefault());
	}

	// getters & setters

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}

	public long getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getPublicationTime() {
		return publicationTime;
	}
	public void setPublicationTime(long publicationTime) {
		this.publicationTime = publicationTime;
	}

	public long getLastModificationTime() {
		return lastModificationTime;
	}
	public void setLastModificationTime(long lastModificationTime) {
		this.lastModificationTime = lastModificationTime;
	}

}
