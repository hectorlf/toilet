package com.hectorlopezfernandez.blog.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hectorlopezfernandez.blog.BaseTest;

public class ServiceTests extends BaseTest {

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	public void setup() {
		User p = new User();
		p.setUsername("test");
		userRepository.save(p);
	}

	@AfterEach
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
		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			securityService.loadUserByUsername("doesnotexist");
		});
	}

}