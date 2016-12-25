package com.hectorlopezfernandez.blog.auth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.hectorlopezfernandez.blog.BaseTest;

public class ServiceTests extends BaseTest {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void setup() {
		User p = new User();
		p.setUsername("test");
		userRepository.save(p);
	}

	@After
	public void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	public void testLoadUserByUsername1() {
		Assert.notNull(securityService.loadUserByUsername("test"));
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsername2() {
		securityService.loadUserByUsername("doesnotexist");
	}

}