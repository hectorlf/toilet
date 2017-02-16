package com.hectorlopezfernandez.blog.metadata;

import java.util.List;

public interface MetadataService {

	// system languages

	/**
	 * Returns the list of all configured languages
	 */
	List<Language> findAllLanguages();

	/**
	 * Returns the default language for the blog
	 */
	Language getDefaultLanguage();

	
	// blog preferences

	/**
	 * Returns the global preferences
	 */
	Preferences getPreferences();

}
