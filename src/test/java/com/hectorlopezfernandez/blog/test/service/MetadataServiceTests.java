package com.hectorlopezfernandez.blog.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.MetadataService;
import com.hectorlopezfernandez.blog.metadata.Preferences;
import com.hectorlopezfernandez.blog.test.BaseTest;

public class MetadataServiceTests extends BaseTest {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private MetadataService metadataService;
	
	private List<Language> languages = new ArrayList<>(2);

	@Test
	public void testGetPreferences() {
		Preferences p = metadataService.getPreferences();
		Assert.assertNotNull(p);
		Assert.assertEquals("Title", p.getTitle());
	}

	@Test
	public void testFindAllLanguages() {
		List<Language> languages = metadataService.findAllLanguages();
		Assert.assertNotNull(languages);
		Assert.assertTrue(languages.size() == 2);
		Language l = metadataService.getDefaultLanguage();
		Assert.assertNotNull(languages);
		Assert.assertEquals("es", l.getLangCode());
	}

	@Before
	public void setup() {
		Preferences p = new Preferences();
		p.setMaxPostAgeInDaysForFeeds(30);
		p.setPaginateIndexPage(true);
		p.setPostsPerIndexPage(3);
		p.setTagline("Tagline");
		p.setTitle("Title");
		mongoTemplate.save(p);
		Language l = new Language();
		l.setDefaultLanguage(true);
		l.setLangCode("es");
		l.setRegionCode("ES");
		mongoTemplate.save(l);
		languages.add(l);
		l = new Language();
		l.setDefaultLanguage(false);
		l.setLangCode("en");
		l.setRegionCode("US");
		mongoTemplate.save(l);
		languages.add(l);
	}

	@After
	public void teardown() {
		mongoTemplate.remove(new Preferences());
		for (Language l : languages) {
			mongoTemplate.remove(l);
		}
	}

}