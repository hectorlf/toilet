package com.hectorlopezfernandez.blog.metadata;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataServiceImpl implements MetadataService {

	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private PreferencesRepository preferencesRepository;

	@Override
	public List<Language> findSupportedLanguages() {
		List<Language> results = languageRepository.findAll();
		return results;
	}

	@Override
	public Set<String> findSupportedLanguageTags() {
		List<Language.TagOnly> tags = languageRepository.findAllTags();
		return tags.stream().map(dto -> dto.getTag()).collect(toSet());
	}

	@Override
	public Language getDefaultLanguage() {
		Preferences preferences = preferencesRepository.findOne(Preferences.ID);
		Language defaultLanguage = languageRepository.findByTag(preferences.getDefaultLanguage());
		return defaultLanguage;
	}

	@Override
	public void addLanguage(Language language) {
		if (language == null) throw new IllegalArgumentException("Language argument cannot be null");
		languageRepository.save(language);
	}

	@Override
	public void removeAllLanguages() {
		languageRepository.deleteAll();
	}

	@Override
	public Preferences getPreferences() {
		Preferences preferences = preferencesRepository.findOne(Preferences.ID);
		return preferences;
	}

	@Override
	public void overwritePreferences(Preferences preferences) {
		if (preferences == null) throw new IllegalArgumentException("Preferences argument cannot be null");
		preferencesRepository.save(preferences);
	}

}