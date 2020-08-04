package com.hectorlopezfernandez.blog.metadata;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Deals with logic around Languages and Preferences.
 */
@Service
public class MetadataService {

	@Inject
	private LanguageRepository languageRepository;
	@Inject
	private PreferencesRepository preferencesRepository;

	// system languages

	/**
	 * Returns the list of all configured languages.
	 * 
	 * @return  List of languages available in the system
	 */
	public List<Language> findSupportedLanguages() {
		List<Language> results = languageRepository.findAll();
		return results;
	}

	/**
	 * Returns a view of the supported languages in the form of a set of language tags,
	 * compatible with {@link java.util.Locale#forLanguageTag(String)}.
	 * 
	 * @return  List of languages available in the system
	 */
	public Set<String> findSupportedLanguageTags() {
		List<Language.TagOnly> tags = languageRepository.findAllTags();
		return tags.stream().map(dto -> dto.getTag()).collect(toSet());
	}

	/**
	 * Returns the default language for the blog.
	 * 
	 * @return  The default language configured for the system, or null if the DB is empty
	 */
	public Language getDefaultLanguage() {
		Preferences preferences = preferencesRepository.get();
		if (preferences == null) return null;
		Language defaultLanguage = languageRepository.findByTag(preferences.getDefaultLanguage());
		return defaultLanguage;
	}

	public void addLanguage(Language language) {
		if (language == null) throw new IllegalArgumentException("Language argument cannot be null");
		languageRepository.save(language);
	}

	public void removeAllLanguages() {
		languageRepository.deleteAll();
	}

	// blog preferences

	/**
	 * Returns the global preferences.
	 * 
	 * @return  Unique global configuration properties for the system
	 */
	public Preferences getPreferences() {
		Preferences preferences = preferencesRepository.get();
		return preferences;
	}

	public void overwritePreferences(Preferences preferences) {
		if (preferences == null) throw new IllegalArgumentException("Preferences argument cannot be null");
		preferencesRepository.save(preferences);
	}

	// initialization and backup
	
	public boolean isAlreadyInitialized() {
		return preferencesRepository.exists();
	}
	
	public void initialize() {
		Language english = new Language();
		english.setTag("en");
		languageRepository.save(english);
		Preferences prefs = new Preferences();
		prefs.setDefaultLanguage("en");
		prefs.setMaxElementsPerPage(10);
		prefs.setPaginateIndexPage(false);
		prefs.setPostAgeLimitForFeed(30*24*60*60*1000l);
		prefs.setTagline("Your blog is ready to start rolling...");
		prefs.setTitle("It's alive!!");
		preferencesRepository.save(prefs);
	}

}