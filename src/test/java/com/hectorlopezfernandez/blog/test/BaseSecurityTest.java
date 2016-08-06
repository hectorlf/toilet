package com.hectorlopezfernandez.blog.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hectorlopezfernandez.blog.Application;
import com.hectorlopezfernandez.blog.auth.Principal;
import com.hectorlopezfernandez.blog.metadata.Language;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestApplicationPersistence.class,Application.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class BaseSecurityTest {

	protected static final String ADMIN_USERNAME = "admin";
	protected static final String ADMIN_PASSWORD = BCrypt.hashpw("admin", BCrypt.gensalt());
	protected static final String USER_USERNAME = "user";
	protected static final String USER_PASSWORD = BCrypt.hashpw("user", BCrypt.gensalt());

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void setup() {
		// database defaults go before mockmvc
		Principal admin = new Principal();
		admin.setId("1");
		admin.setEnabled(true);
		admin.setUsername(ADMIN_USERNAME);
		admin.setPassword(ADMIN_PASSWORD);
		mongoTemplate.insert(admin);
		Principal user = new Principal();
		user.setId("2");
		user.setEnabled(true);
		user.setUsername(USER_USERNAME);
		user.setPassword(USER_PASSWORD);
		mongoTemplate.insert(user);
		Language l = new Language();
		l.setId("1");
		l.setDefaultLanguage(true);
		l.setLangCode("es");
		mongoTemplate.insert(l);

		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.build();
	}

	@After
	public void tearDown() {
		Principal p = new Principal();
		p.setId("1");
		mongoTemplate.remove(p);
		p.setId("2");
		mongoTemplate.remove(p);
		Language l = new Language();
		l.setId("1");
		mongoTemplate.remove(l);
	}

}