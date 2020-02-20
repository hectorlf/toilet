package com.hectorlopezfernandez.blog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.MetadataService;

@SpringBootTest(classes={TestApplicationPersistence.class,Application.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class BaseMvcTest {

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		// database defaults go before mockmvc
		Language l = new Language();
		l.setTag("en");
		metadataService.addLanguage(l);
		l = new Language();
		l.setTag("es");
		metadataService.addLanguage(l);
		l = new Language();
		l.setTag("es-ES");
		metadataService.addLanguage(l);
		l = new Language();
		l.setTag("pt");
		metadataService.addLanguage(l);

		mockMvc = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.build();
	}

	@AfterEach
	public void tearDown() {
		metadataService.removeAllLanguages();
	}

}