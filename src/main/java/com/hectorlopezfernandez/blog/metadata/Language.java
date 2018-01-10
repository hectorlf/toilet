package com.hectorlopezfernandez.blog.metadata;

import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="languages")
public class Language {

	@Id
	private String id;
	@Indexed(unique=true)
	private String tag;

	// utility methods
	
	public Locale toLocale() {
		return Locale.forLanguageTag(tag);
	}

	// getters & setters

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	// projection interfaces
	
	interface TagOnly {
		String getTag();
	}

}