package com.hectorlopezfernandez.blog.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hectorlopezfernandez.blog.Application;
import com.hectorlopezfernandez.blog.metadata.Language;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestApplicationPersistence.class,Application.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class BaseMvcTest {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void setup() {
		// database defaults go before mockmvc
		Language l = new Language();
		l.setId("1");
		l.setDefaultLanguage(true);
		l.setLangCode("en");
		mongoTemplate.insert(l);
		l.setId("2");
		l.setDefaultLanguage(false);
		l.setLangCode("es");
		mongoTemplate.insert(l);
		l.setId("3");
		l.setDefaultLanguage(false);
		l.setLangCode("es");
		l.setRegionCode("ES");
		mongoTemplate.insert(l);

		mockMvc = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.build();
	}

	@After
	public void tearDown() {
		Language l = new Language();
		l.setId("1");
		mongoTemplate.remove(l);
		l.setId("2");
		mongoTemplate.remove(l);
		l.setId("3");
		mongoTemplate.remove(l);
	}

}