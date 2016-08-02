package com.hectorlopezfernandez.blog.metadata;

import java.util.List;

public interface MetadataService {

	/**
	 * Returns the list of all configured languages
	 */
	List<Language> findAllLanguages();

	/**
	 * Returns the default language for the blog
	 */
	Language getDefaultLanguage();

}
