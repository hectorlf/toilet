package com.hectorlopezfernandez.blog.metadata;

/**
 * Stores the result of a translation request to the admin REST API.
 * 
 * @author hector
 */
public class Translation {

	public final String key;
	public final String value;
	public final String requestedLocale;

	// constructors

	public Translation(String key, String value, String requestedLocale) {
		this.key = key;
		this.value = value;
		this.requestedLocale = requestedLocale;
	}

}
