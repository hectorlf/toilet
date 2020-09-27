package com.hectorlopezfernandez.blog.post;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Post contains data about a single entry, which forms
 * the basis of a blog.
 * 
 * Implementation notes:
 * - slug stores the URL path segment, after /posts/year/month, that uniquely identify
 *   the post and has to conform to URL syntax
 * - metaDescription stores a meta that can be crawled by search engines, and has
 *   to be properly HTML encoded
 * - title contains a text that will be used in the layout, and has to be properly HTML encoded
 * - content is the proper post HTML body, including markup (relative to the layout container)
 * - excerpt will typically store a summary of the post, in HTML including markup, although
 *   different layouts could have different uses for this
 * - feedContent is a performance optimization: upon saving a post, a feed-compatible content
 *   will be computed and stored here for quicker responses
 * 
 * Restrictions: slug is unique in the DB
 * 
 * @author hector
 */
@Document(collection="posts")
public class Post {

	@Id
	private String id;
	private String title;
	private String slug;

	private String metaDescription;
	private String excerpt;
	private String content;

	// preprocessed text for feeds
	private String feedContent;

	private long creationTime;
	private long publicationTime;
	private long lastModificationTime;
	private boolean published;
	
	private boolean commentsAllowed;

	private String author;

	private Collection<String> tags;

//	private Collection<Post> relatedPosts;

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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getFeedContent() {
		return feedContent;
	}
	public void setFeedContent(String feedContent) {
		this.feedContent = feedContent;
	}

	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isCommentsAllowed() {
		return commentsAllowed;
	}
	public void setCommentsAllowed(boolean commentsAllowed) {
		this.commentsAllowed = commentsAllowed;
	}

	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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

	public Collection<String> getTags() {
		return tags;
	}
	public void setTags(Collection<String> tags) {
		this.tags = tags;
	}

	// projections

	public static interface SitemapProjection {
		String getSlug();
		long getPublicationTime();
		long getLastModificationTime();
	}

}
