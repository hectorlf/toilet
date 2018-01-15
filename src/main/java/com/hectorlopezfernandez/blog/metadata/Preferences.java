package com.hectorlopezfernandez.blog.metadata;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * System preferences are stored in a single Preferences object, as
 * currently there's no support for multi-blog deployments. Although
 * not a singleton Java-wise, there can only be a single representation
 * of this class in the DB.
 */
@Document(collection="preferences")
public class Preferences {

	// Only allow one preferences object (one blog)
	static final String ID = "1";

	@Id
	private String id = ID;
	private String title;
	private String tagline;
	private boolean paginateIndexPage;
	private int maxElementsPerPage;
	private long postAgeLimitForFeed;
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

	public String getDefaultLanguage() {
		return defaultLanguage;
	}
	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	/**
	 * Returns the max age for a Post to appear in the feeds.
	 * NOTE: measured in milliseconds.
	 * 
	 * @return  Post age limit to appear in feeds, in milliseconds 
	 */
	public long getPostAgeLimitForFeed() {
		return postAgeLimitForFeed;
	}
	/**
	 * Sets the max age for a Post to appear in the feeds.
	 * NOTE: measured in milliseconds.
	 * 
	 * @param postAgeLimitForFeed  maximum number of milliseconds
	 */
	public void setPostAgeLimitForFeed(long postAgeLimitForFeed) {
		this.postAgeLimitForFeed = postAgeLimitForFeed;
	}

	public boolean isPaginateIndexPage() {
		return paginateIndexPage;
	}
	public void setPaginateIndexPage(boolean paginateIndexPage) {
		this.paginateIndexPage = paginateIndexPage;
	}

	public int getMaxElementsPerPage() {
		return maxElementsPerPage;
	}
	public void setMaxElementsPerPage(int maxElementsPerPage) {
		this.maxElementsPerPage = maxElementsPerPage;
	}

}
