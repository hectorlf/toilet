package com.hectorlopezfernandez.blog.auth;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;
import com.hectorlopezfernandez.blog.auth.Principal;
import com.hectorlopezfernandez.blog.auth.PrincipalRepository;

public class PrincipalRepositoryTests extends BaseTest {
	
	@Autowired
	private PrincipalRepository principalRepository;
	
	private List<Principal> principals = new ArrayList<>(2);

	@Test
	public void testPrincipals() {
		Assert.assertTrue(principalRepository.findAll().size() == 2);
		Principal p = principalRepository.findByUsername("User2");
		Assert.assertNotNull(p);
		Assert.assertEquals("User2", p.getUsername());
		Assert.assertNull(principalRepository.findByUsername("nonexistent"));
	}

	@Before
	public void setup() {
		Principal p = new Principal();
		p.setEnabled(true);
		p.setPassword("Password");
		p.setUsername("User1");
		principals.add(principalRepository.save(p));
		p = new Principal();
		p.setEnabled(false);
		p.setPassword("Password");
		p.setUsername("User2");
		principals.add(principalRepository.save(p));
	}

	@After
	public void teardown() {
		principalRepository.delete(principals);
	}

}