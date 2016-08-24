package com.hectorlopezfernandez.blog.test.repository;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.LanguageRepository;
import com.hectorlopezfernandez.blog.metadata.Preferences;
import com.hectorlopezfernandez.blog.metadata.PreferencesRepository;
import com.hectorlopezfernandez.blog.test.BaseTest;

public class MetadataRepositoryTests extends BaseTest {

	@Autowired
	private PreferencesRepository preferencesRepository;
	@Autowired
	private LanguageRepository languageRepository;

	@Test
	public void testPreferences() {
		Preferences p = createPreferences();
		preferencesRepository.save(p);

		Assert.assertTrue(preferencesRepository.findAll().size() == 1);
		p = preferencesRepository.findOne(Preferences.ID);
		Assert.assertEquals("Title", p.getTitle());
	}

	@Test
	public void testLanguages() {
		Language l = createLanguage1();
		languageRepository.save(l);
		l = createLanguage2();
		languageRepository.save(l);

		Assert.assertTrue(languageRepository.findAll().size() == 2);
		l = languageRepository.findByDefaultLanguageIsTrue();
		Assert.assertNotNull(l);
		Assert.assertTrue(l.toLocale().equals(new Locale("es", "ES")));
	}

	private Preferences createPreferences() {
		Preferences p = new Preferences();
		p.setMaxPostAgeInDaysForFeeds(30);
		p.setPaginateIndexPage(true);
		p.setPostsPerIndexPage(3);
		p.setTagline("Tagline");
		p.setTitle("Title");
		return p;
	}

	private Language createLanguage1() {
		Language l = new Language();
		l.setDefaultLanguage(true);
		l.setLangCode("es");
		l.setRegionCode("ES");
		return l;
	}
	private Language createLanguage2() {
		Language l = new Language();
		l.setDefaultLanguage(false);
		l.setLangCode("en");
		l.setRegionCode("US");
		return l;
	}

}