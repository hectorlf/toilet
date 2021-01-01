package com.hectorlopezfernandez.toilet.author;

import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.toilet.BaseTest;

public class AuthorRepositoryTests extends BaseTest {
	
	@Autowired
	private AuthorRepository authorRepository;
/*
	@Test
	public void testAuthorities() {
		Assertions.assertTrue(authorityRepository.findAll().size() == 2);
		Authority a = authorityRepository.findByAuthority("Auth2");
		Assertions.assertNotNull(a);
		Assertions.assertEquals("Auth2", a.getAuthority());
		Assertions.assertNull(authorityRepository.findByAuthority("nonexistent"));
	}

	@BeforeEach
	public void setup() {
		Authority a = new Authority();
		a.setAuthority("Auth1");
		authorityRepository.save(a);
		a = new Authority();
		a.setAuthority("Auth2");
		authorityRepository.save(a);
	}

	@AfterEach
	public void teardown() {
		authorityRepository.deleteAll();
	}
*/
}