package com.hectorlopezfernandez.blog.metadata;

import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class LanguageRepositoryTests extends BaseTest {

	@Autowired
	private LanguageRepository languageRepository;
	
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
		languageRepository.save(l);
		l = new Language();
		l.setDefaultLanguage(false);
		l.setLangCode("en");
		l.setRegionCode("US");
		languageRepository.save(l);
	}

	@After
	public void teardown() {
		languageRepository.deleteAll();
	}

}