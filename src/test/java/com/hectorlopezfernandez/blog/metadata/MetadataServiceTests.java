package com.hectorlopezfernandez.blog.metadata;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

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
		Assert.assertNotNull(p);
		Assert.assertEquals("Title", p.getTitle());
	}

	@Test
	public void testFindAllLanguages() {
		List<Language> languages = metadataService.findSupportedLanguages();
		Assert.assertNotNull(languages);
		Assert.assertTrue(languages.size() == 2);
		Language l = metadataService.getDefaultLanguage();
		Assert.assertNotNull(languages);
		Assert.assertEquals("es-ES", l.getTag());
	}

	@Before
	public void setup() {
		Preferences p = new Preferences();
		p.setMaxPostAgeInDaysForFeeds(30);
		p.setPaginateIndexPage(true);
		p.setPostsPerIndexPage(3);
		p.setTagline("Tagline");
		p.setTitle("Title");
		p.setDefaultLanguage("es-ES");
		preferencesRepository.save(p);
		Language l = new Language();
		l.setTag("es-ES");
		languageRepository.save(l);
		l = new Language();
		l.setTag("en-US");
		languageRepository.save(l);
	}

	@After
	public void teardown() {
		preferencesRepository.deleteAll();
		languageRepository.deleteAll();
	}

}