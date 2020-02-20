package com.hectorlopezfernandez.blog.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class UserRepositoryTests extends BaseTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@BeforeEach
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
		Assertions.assertTrue(userRepository.findAll().size() == 2);
		User u = userRepository.findByUsername("User2");
		Assertions.assertNotNull(u);
		Assertions.assertEquals("User2", u.getUsername());
		Assertions.assertNull(userRepository.findByUsername("nonexistent"));
	}

	@AfterEach
	public void teardown() {
		userRepository.deleteAll();
	}

}