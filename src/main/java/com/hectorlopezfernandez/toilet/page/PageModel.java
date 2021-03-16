package com.hectorlopezfernandez.toilet.page;

import java.time.LocalDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * View of a Page as seen by the admin API
 * 
 * @author hector
 * @see Page
 */
@Relation(itemRelation = "page", collectionRelation = "pages")
public class PageModel extends RepresentationModel<PageModel> {

	private String id;
	@NonNull
	private String slug;
	private String metaDescription;
	@NonNull
	private String title;
	@NonNull
	private String content;
	private boolean published;
	private LocalDateTime publicationTime;

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

	public String getMetaDescription() {
		return metaDescription;
	}
	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public boolean isPublished() {
		return published;
	}
	public void setPublished(boolean published) {
		this.published = published;
	}

	public LocalDateTime getPublicationTime() {
		return publicationTime;
	}
	public void setPublicationTime(LocalDateTime publicationTime) {
		this.publicationTime = publicationTime;
	}

	// equals and hashcode

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj) return true;
		if (this.slug == null) return false;
		if (obj == null || !obj.getClass().equals(this.getClass())) return false;
		PageModel that = (PageModel) obj;
		return this.slug.equals(that.slug);
	}

	@Override
	public int hashCode() {
		int result = 17;
		if (slug != null) result += 31 * slug.hashCode();
		return result;
	}

}
