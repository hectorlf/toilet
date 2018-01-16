package com.hectorlopezfernandez.blog.user;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;
import com.hectorlopezfernandez.blog.user.Authority;
import com.hectorlopezfernandez.blog.user.AuthorityRepository;

public class AuthorityRepositoryTests extends BaseTest {
	
	@Autowired
	private AuthorityRepository authorityRepository;

	@Test
	public void testAuthorities() {
		Assert.assertTrue(authorityRepository.findAll().size() == 2);
		Authority a = authorityRepository.findByAuthority("Auth2");
		Assert.assertNotNull(a);
		Assert.assertEquals("Auth2", a.getAuthority());
		Assert.assertNull(authorityRepository.findByAuthority("nonexistent"));
	}

	@Before
	public void setup() {
		Authority a = new Authority();
		a.setAuthority("Auth1");
		authorityRepository.save(a);
		a = new Authority();
		a.setAuthority("Auth2");
		authorityRepository.save(a);
	}

	@After
	public void teardown() {
		authorityRepository.deleteAll();
	}

}