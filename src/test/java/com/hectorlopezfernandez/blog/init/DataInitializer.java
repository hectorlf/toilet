package com.hectorlopezfernandez.blog.init;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;
import com.hectorlopezfernandez.blog.auth.Authority;
import com.hectorlopezfernandez.blog.auth.AuthorityRepository;
import com.hectorlopezfernandez.blog.auth.User;
import com.hectorlopezfernandez.blog.auth.UserRepository;
import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.LanguageRepository;
import com.hectorlopezfernandez.blog.metadata.Preferences;
import com.hectorlopezfernandez.blog.metadata.PreferencesRepository;

/**
 * Initializes a db. Used to generate the initial json data without
 * wanting to rip your eyes off.
 * 
 * Test @ignored by default.
 * 
 * @author hectorlf
 */
public class DataInitializer extends BaseTest {

	@Autowired
	private PreferencesRepository preferencesRepository;
	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;

	@Test
	@Ignore
	public void initialize() {
		// languages
		Language l = new Language();
		l.setPrimary(true);
		l.setLangCode("es");
		l.setRegionCode("ES");
		languageRepository.save(l);
		l = new Language();
		l.setLangCode("en");
		l.setRegionCode("US");
		languageRepository.save(l);

		// authorities
		Authority a = new Authority();
		a.setAuthority("ROLE_ADMIN");
		authorityRepository.save(a);

		// preferences
		Preferences p = new Preferences();
		p.setMaxPostAgeInDaysForFeeds(30);
		p.setPaginateIndexPage(true);
		p.setPostsPerIndexPage(3);
		p.setTagline("Tagline");
		p.setTitle("Title");
		preferencesRepository.save(p);

		// users
		User u = new User();
		u.setAbout("About...");
		u.setAuthorities(Arrays.asList(new Authority[] { a }));
		u.setDisplayName("Admin");
		u.setEnabled(true);
		u.setLanguage(new User.Language("es", "ES"));
		u.setPassword("supersecret");
		u.setRelatedUrl("http://go.to/toilet");
		u.setUsername("admin");
		userRepository.save(u);
	}

}