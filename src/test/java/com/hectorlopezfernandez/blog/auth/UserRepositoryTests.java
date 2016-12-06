package com.hectorlopezfernandez.blog.auth;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;
import com.hectorlopezfernandez.blog.auth.User;
import com.hectorlopezfernandez.blog.auth.UserRepository;
import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.LanguageRepository;

public class UserRepositoryTests extends BaseTest {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LanguageRepository languageRepository;
	
	private List<User> users = new ArrayList<>(2);
	private List<Language> languages = new ArrayList<>(1);

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
		Language l = new Language();
		l.setDefaultLanguage(true);
		l.setLangCode("en");
		l.setRegionCode("US");
		languages.add(languageRepository.save(l));
		User u = new User();
		u.setUsername("User1");
		u.setLanguage(l);
		users.add(userRepository.save(u));
		u = new User();
		u.setUsername("User2");
		u.setLanguage(l);
		users.add(userRepository.save(u));
	}

	@After
	public void teardown() {
		userRepository.delete(users);
		languageRepository.delete(languages);
	}

}