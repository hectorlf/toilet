package com.hectorlopezfernandez.blog.test.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.auth.Authority;
import com.hectorlopezfernandez.blog.auth.AuthorityRepository;
import com.hectorlopezfernandez.blog.test.BaseTest;

public class AuthorityRepositoryTests extends BaseTest {
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	private List<Authority> authorities = new ArrayList<>(2);

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
		authorities.add(authorityRepository.save(a));
		a = new Authority();
		a.setAuthority("Auth2");
		authorities.add(authorityRepository.save(a));
	}

	@After
	public void teardown() {
		authorityRepository.delete(authorities);
	}

}