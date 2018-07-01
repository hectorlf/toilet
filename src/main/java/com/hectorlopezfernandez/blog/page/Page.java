package com.hectorlopezfernandez.blog.page;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Page contains data about a single static web page,
 * that can be linked and shown as "everything else" that's
 * not a blog post.
 * 
 * Implementation notes:
 * - metaDescription contains a meta that can be crawled by search engines, and has
 *   to be properly HTML encoded
 * - title contains a text that will be used in the HTML head and optionally in
 *   other places of a layout, and has to be properly HTML encoded
 * - content is the proper page HTML body, including markup (relative to the layout
 *   container) 
 * 
 * @author hector
 */
@Document(collection="pages")
public class Page {

	@Id
	private String id;
	@Indexed(unique=true)
	private String slug;

	private String metaDescription;
	private String title;
	private String content;

	@Indexed
	private boolean published;
	private long publicationDate;
	private long lastModificationDate;

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

	public long getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(long publicationDate) {
		this.publicationDate = publicationDate;
	}

	public long getLastModificationDate() {
		return lastModificationDate;
	}
	public void setLastModificationDate(long lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
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

}
