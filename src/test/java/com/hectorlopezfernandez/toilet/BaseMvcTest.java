package com.hectorlopezfernandez.toilet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hectorlopezfernandez.toilet.metadata.Language;
import com.hectorlopezfernandez.toilet.metadata.LanguageRepository;
import com.hectorlopezfernandez.toilet.metadata.Preferences;
import com.hectorlopezfernandez.toilet.metadata.PreferencesRepository;

@SpringBootTest(classes={Application.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class BaseMvcTest {

	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private PreferencesRepository preferencesRepository;
	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		// database defaults go before mockmvc
		Language l = new Language();
		l.setId("1");
		l.setTag("en");
		languageRepository.save(l);
		l = new Language();
		l.setId("2");
		l.setTag("es");
		languageRepository.save(l);
		l = new Language();
		l.setId("3");
		l.setTag("es-ES");
		languageRepository.save(l);
		l = new Language();
		l.setId("4");
		l.setTag("pt");
		languageRepository.save(l);
		Preferences p = new Preferences();
		p.setPostAgeLimitForFeed(Long.valueOf(30*24*60*60*1000));
		p.setPaginationEnabledForIndexPage(true);;
		p.setMaxElementsPerPage(3);
		p.setTagline("Tagline");
		p.setTitle("Title");
		p.setDefaultLanguage("3");
		preferencesRepository.save(p);

		mockMvc = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.build();
	}

	@AfterEach
	public void tearDown() {
		languageRepository.deleteAll();
		preferencesRepository.deleteAll();
	}

}