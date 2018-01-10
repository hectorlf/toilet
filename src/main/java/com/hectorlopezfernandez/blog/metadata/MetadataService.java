package com.hectorlopezfernandez.blog.metadata;

import java.util.List;
import java.util.Set;

public interface MetadataService {

	// system languages

	/**
	 * Returns the list of all configured languages
	 * 
	 * @return  List of languages available in the system
	 */
	List<Language> findSupportedLanguages();

	/**
	 * Returns a view of the supported languages in the form of a set of language tags,
	 * compatible with {@link java.util.Locale#forLanguageTag(String)}
	 * 
	 * @return  List of languages available in the system
	 */
	Set<String> findSupportedLanguageTags();

	/**
	 * Returns the default language for the blog
	 * @return  The default language configured for the system
	 */
	Language getDefaultLanguage();
	
	void addLanguage(Language language);
	void removeAllLanguages();

	
	// blog preferences

	/**
	 * Returns the global preferences
	 * @return  Unique global configuration properties for the system
	 */
	Preferences getPreferences();

	void overwritePreferences(Preferences preferences);

}
