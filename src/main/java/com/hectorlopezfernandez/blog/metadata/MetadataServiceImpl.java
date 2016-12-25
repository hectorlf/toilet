package com.hectorlopezfernandez.blog.metadata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataServiceImpl implements MetadataService {

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private PreferencesRepository preferencesRepository;

	@Override
	public List<Language> findAllLanguages() {
		List<Language> results = languageRepository.findAll();
		return results;
	}

	@Override
	public Language getDefaultLanguage() {
		Language defaultLanguage = languageRepository.findByPrimaryIsTrue();
		return defaultLanguage;
	}

	@Override
	public Preferences getPreferences() {
		Preferences preferences = preferencesRepository.findOne(Preferences.ID);
		return preferences;
	}

}