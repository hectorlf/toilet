package com.hectorlopezfernandez.blog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.MetadataService;
import com.hectorlopezfernandez.blog.metadata.Preferences;
import com.hectorlopezfernandez.blog.security.SecurityService;
import com.hectorlopezfernandez.blog.security.User;

@SpringBootTest(classes={TestApplicationPersistence.class,Application.class}, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class BaseSecurityTest {

	protected static final String ADMIN_USERNAME = "admin";
	protected static final String ADMIN_PASSWORD = "adminpass";
	protected static final String ADMIN_PASSWORD_ENCRYPTED = BCrypt.hashpw(ADMIN_PASSWORD, BCrypt.gensalt());
	protected static final String USER_USERNAME = "user";
	protected static final String USER_PASSWORD = "userpass";
	protected static final String USER_PASSWORD_ENCRYPTED = BCrypt.hashpw(USER_PASSWORD, BCrypt.gensalt());

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private WebApplicationContext wac;
	protected MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		// database defaults go before mockmvc
		User admin = new User();
		admin.setEnabled(true);
		admin.setUsername(ADMIN_USERNAME);
		admin.setPassword(ADMIN_PASSWORD_ENCRYPTED);
		admin.setLanguage("es");
		securityService.addUser(admin);
		User user = new User();
		user.setEnabled(true);
		user.setUsername(USER_USERNAME);
		user.setPassword(USER_PASSWORD_ENCRYPTED);
		admin.setLanguage("es");
		securityService.addUser(user);
		Language l = new Language();
		l.setTag("es");
		metadataService.addLanguage(l);
		Preferences p = new Preferences();
		p.setDefaultLanguage("es");
		metadataService.overwritePreferences(p);

		this.mockMvc = MockMvcBuilders
			.webAppContextSetup(this.wac)
			.apply(SecurityMockMvcConfigurers.springSecurity())
			.build();
	}

	@AfterEach
	public void tearDown() {
		metadataService.removeAllLanguages();
		securityService.removeAllUsers();
	}

}