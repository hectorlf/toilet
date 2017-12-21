package com.hectorlopezfernandez.blog;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.LanguageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestApplicationPersistence.class,Application.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class BaseMvcTest {

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void setup() {
		// database defaults go before mockmvc
		Language l = new Language();
		l.setPrimary(true);
		l.setLangCode("en");
		languageRepository.save(l);
		l = new Language();
		l.setLangCode("es");
		languageRepository.save(l);
		l = new Language();
		l.setLangCode("es");
		l.setRegionCode("ES");
		languageRepository.save(l);
		l = new Language();
		l.setLangCode("pt");
		languageRepository.save(l);

		mockMvc = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.build();
	}

	@After
	public void tearDown() {
		languageRepository.deleteAll();
	}

}