package com.hectorlopezfernandez.blog.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;
import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.LanguageRepository;

public class LanguageRepositoryTests extends BaseTest {

	@Autowired
	private LanguageRepository languageRepository;
	
	private List<Language> languages = new ArrayList<>(2);

	@Test
	public void testLanguages() {
		Assert.assertTrue(languageRepository.findAll().size() == 2);
		Language l = languageRepository.findByDefaultLanguageIsTrue();
		Assert.assertNotNull(l);
		Assert.assertTrue(l.toLocale().equals(new Locale("es", "ES")));
	}

	@Before
	public void setup() {
		Language l = new Language();
		l.setDefaultLanguage(true);
		l.setLangCode("es");
		l.setRegionCode("ES");
		languages.add(languageRepository.save(l));
		l = new Language();
		l.setDefaultLanguage(false);
		l.setLangCode("en");
		l.setRegionCode("US");
		languages.add(languageRepository.save(l));
	}

	@After
	public void teardown() {
		languageRepository.delete(languages);
	}

}