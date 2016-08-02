package com.hectorlopezfernandez.blog.test.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.hectorlopezfernandez.blog.auth.SecurityService;
import com.hectorlopezfernandez.blog.test.BaseTest;

public class SecurityServiceTests extends BaseTest {

	@Autowired
	private SecurityService securityService;

	@Test
	public void testLoadUserByUsername1() {
		Assert.notNull(securityService.loadUserByUsername("test"));
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testLoadUserByUsername2() {
		securityService.loadUserByUsername("doesnotexist");
	}

}