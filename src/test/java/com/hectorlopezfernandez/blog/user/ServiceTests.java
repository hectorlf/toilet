package com.hectorlopezfernandez.blog.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hectorlopezfernandez.blog.BaseTest;

public class ServiceTests extends BaseTest {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserRepository userRepository;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

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
	public void testLoadUserByUsername_usernameExists_userIsReturned() {
		UserDetails loadedUser = securityService.loadUserByUsername("test");
		assertThat(loadedUser, is(notNullValue()));
	}

	@Test
	public void testLoadUserByUsername2() {
		expectedException.expect(UsernameNotFoundException.class);
		securityService.loadUserByUsername("doesnotexist");
	}

}