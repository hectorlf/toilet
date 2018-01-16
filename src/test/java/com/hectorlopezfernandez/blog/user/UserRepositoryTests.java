package com.hectorlopezfernandez.blog.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;
import com.hectorlopezfernandez.blog.user.User;
import com.hectorlopezfernandez.blog.user.UserRepository;

public class UserRepositoryTests extends BaseTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void setup() {
		User u = new User();
		u.setUsername("User1");
		u.setLanguage("en");
		userRepository.save(u);
		u = new User();
		u.setUsername("User2");
		u.setLanguage("en");
		userRepository.save(u);
	}

	@Test
	public void testUsers() {
		Assert.assertTrue(userRepository.findAll().size() == 2);
		User u = userRepository.findByUsername("User2");
		Assert.assertNotNull(u);
		Assert.assertEquals("User2", u.getUsername());
		Assert.assertNull(userRepository.findByUsername("nonexistent"));
	}

	@After
	public void teardown() {
		userRepository.deleteAll();
	}

}