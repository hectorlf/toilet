package com.hectorlopezfernandez.toilet.metadata;

import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Language objects represent available system languages. They're used to
 * limit translations of the UI to a few well-known options. Possibly,
 * in the future, they could be used to offer content translations also.
 */
@Document(collection="languages")
public class Language {

	@Id
	private String id;
	private String tag;

	// constructors

	public Language() {
	}

	public Language(String tag) {
		this.tag = tag;
	}

	// utility methods
	
	/**
	 * Translates this object's language tag to a Java Locale.
	 * 
	 * @return  the corresponding {@link Locale} for this object's language
	 */
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