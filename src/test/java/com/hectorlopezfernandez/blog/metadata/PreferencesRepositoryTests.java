package com.hectorlopezfernandez.blog.metadata;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class PreferencesRepositoryTests extends BaseTest {

	@Autowired
	private PreferencesRepository preferencesRepository;

	@Test
	public void testPreferences() {
		Assert.assertTrue(preferencesRepository.findAll().size() == 1);
		Preferences p = preferencesRepository.get();
		Assert.assertEquals("Title", p.getTitle());
	}

	@Before
	public void setup() {
		Preferences p = new Preferences();
		p.setMaxElementsPerPage(3);
		p.setPaginateIndexPage(true);
		p.setPostAgeLimitForFeed(30*24*60*60*1000);
		p.setTagline("Tagline");
		p.setTitle("Title");
		p.setDefaultLanguage("es");
		preferencesRepository.save(p);
	}

	@After
	public void teardown() {
		preferencesRepository.deleteAll();
	}

}