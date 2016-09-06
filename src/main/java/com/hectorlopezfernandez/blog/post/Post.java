package com.hectorlopezfernandez.blog.post;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hectorlopezfernandez.blog.auth.User;

/**
 * Post data
 * 
 * @author hector
 */
@Document(collection="posts")
public class Post {

	@Id
	private String id;
	
	private String title;
	
	private String titleUrl;

	private String metaDescription;

	private String excerpt;

	private String content;

	// denormalized text for feeds
	private String feedContent;

	private long creationDate;
	private long publicationDate;
	private long lastModificationDate;
	
	private boolean commentsClosed;

	private boolean published;

	@DBRef
	private User author;
	
//	private ArchiveEntry archiveEntry;

//	private Collection<Tag> tags;

//	private Collection<Post> relatedPosts;

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

	public String getTitleUrl() {
		return titleUrl;
	}
	public void setTitleUrl(String titleUrl) {
		this.titleUrl = titleUrl;
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

	public long getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
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

	public boolean isCommentsClosed() {
		return commentsClosed;
	}
	public void setCommentsClosed(boolean commentsClosed) {
		this.commentsClosed = commentsClosed;
	}

	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}

	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}

}
