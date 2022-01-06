package com.hectorlopezfernandez.toilet.metadata;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.toilet.BaseTest;

public class PreferencesRepositoryTests extends BaseTest {

	@Autowired
	private PreferencesRepository preferencesRepository;

	@Test
	public void testPreferences() {
		Assertions.assertTrue(preferencesRepository.findAll().size() == 1);
		Preferences p = preferencesRepository.get();
		Assertions.assertEquals("Title", p.getTitle());
	}

	@BeforeEach
	public void setup() {
		Preferences p = new Preferences();
		p.setMaxElementsPerPage(3);
		p.setPaginationEnabledForIndexPage(true);
		p.setPostAgeLimitForFeed(30*24*60*60*1000);
		p.setTagline("Tagline");
		p.setTitle("Title");
		p.setDefaultLanguage("es");
		preferencesRepository.save(p);
	}

	@AfterEach
	public void teardown() {
		preferencesRepository.deleteAll();
	}

}