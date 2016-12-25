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
		Preferences p = preferencesRepository.findOne(Preferences.ID);
		Assert.assertEquals("Title", p.getTitle());
	}

	@Before
	public void setup() {
		Preferences p = new Preferences();
		p.setMaxPostAgeInDaysForFeeds(30);
		p.setPaginateIndexPage(true);
		p.setPostsPerIndexPage(3);
		p.setTagline("Tagline");
		p.setTitle("Title");
		preferencesRepository.save(p);
	}

	@After
	public void teardown() {
		preferencesRepository.deleteAll();
	}

}