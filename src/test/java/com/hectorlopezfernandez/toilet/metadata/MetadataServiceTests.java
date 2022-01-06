package com.hectorlopezfernandez.toilet.metadata;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.toilet.BaseTest;

public class MetadataServiceTests extends BaseTest {

	@Autowired
	private MetadataService metadataService;
	
	@Autowired
	private PreferencesRepository preferencesRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Test
	public void testGetPreferences() {
		Preferences p = metadataService.getPreferences();
		Assertions.assertNotNull(p);
		Assertions.assertEquals("Title", p.getTitle());
	}

	@Test
	public void testFindAllLanguages() {
		List<Language> languages = metadataService.findSupportedLanguages();
		Assertions.assertNotNull(languages);
		Assertions.assertTrue(languages.size() == 2);
		Language l = metadataService.getDefaultLanguage();
		Assertions.assertNotNull(languages);
		Assertions.assertEquals("es-ES", l.getTag());
	}

	@BeforeEach
	public void setup() {
		Preferences p = new Preferences();
		p.setPostAgeLimitForFeed(Long.valueOf(30*24*60*60*1000));
		p.setPaginationEnabledForIndexPage(true);;
		p.setMaxElementsPerPage(3);
		p.setTagline("Tagline");
		p.setTitle("Title");
		p.setDefaultLanguage("1");
		preferencesRepository.save(p);
		Language l = new Language();
		l.setId("1");
		l.setTag("es-ES");
		languageRepository.save(l);
		l = new Language();
		l.setId("2");
		l.setTag("en-US");
		languageRepository.save(l);
	}

	@AfterEach
	public void teardown() {
		preferencesRepository.deleteAll();
		languageRepository.deleteAll();
	}

}