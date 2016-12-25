package com.hectorlopezfernandez.blog.auth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;
import com.hectorlopezfernandez.blog.metadata.LanguageRepository;

public class UserRepositoryTests extends BaseTest {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LanguageRepository languageRepository;
	
	@Test
	public void testUsers() {
		Assert.assertTrue(userRepository.findAll().size() == 2);
		User u = userRepository.findByUsername("User2");
		Assert.assertNotNull(u);
		Assert.assertEquals("User2", u.getUsername());
		Assert.assertNull(userRepository.findByUsername("nonexistent"));
	}

	@Before
	public void setup() {
		User.Language l = new User.Language();
		l.setLangCode("en");
		l.setRegionCode("US");
		User u = new User();
		u.setUsername("User1");
		u.setLanguage(l);
		userRepository.save(u);
		u = new User();
		u.setUsername("User2");
		u.setLanguage(l);
		userRepository.save(u);
	}

	@After
	public void teardown() {
		userRepository.deleteAll();
		languageRepository.deleteAll();
	}

}