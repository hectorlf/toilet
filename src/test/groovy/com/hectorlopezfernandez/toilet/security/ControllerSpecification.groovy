package com.hectorlopezfernandez.toilet.security

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.web.context.WebApplicationContext

import com.hectorlopezfernandez.toilet.Application
import com.hectorlopezfernandez.toilet.metadata.Language
import com.hectorlopezfernandez.toilet.metadata.MetadataService
import com.hectorlopezfernandez.toilet.metadata.Preferences

import spock.lang.Specification

@SpringBootTest(classes=[Application.class], webEnvironment=WebEnvironment.RANDOM_PORT)
class ControllerSpecification extends Specification {

	static final String ADMIN_USERNAME = 'admin'
	static final String ADMIN_PASSWORD = 'adminpass'
	static final String ADMIN_PASSWORD_ENCRYPTED = BCrypt.hashpw(ADMIN_PASSWORD, BCrypt.gensalt())
	static final String USER_USERNAME = 'user'
	static final String USER_PASSWORD = 'userpass'
	static final String USER_PASSWORD_ENCRYPTED = BCrypt.hashpw(USER_PASSWORD, BCrypt.gensalt())

	@Autowired
	MetadataService metadataService

	@Autowired
	SecurityService securityService

	@Autowired
	WebApplicationContext wac

	MockMvc mockMvc
	
	def setup() {
		User admin = new User(ADMIN_USERNAME, ADMIN_PASSWORD_ENCRYPTED, 'es', true)
		admin.addRole('ADMIN')
		securityService.addUser(admin)
		User user = new User(USER_USERNAME, USER_PASSWORD_ENCRYPTED, 'es', true);
		securityService.addUser(user);
		Language spanish = new Language('es');
		metadataService.addLanguage(spanish);
		Preferences preferences = new Preferences();
		preferences.setDefaultLanguage('es');
		metadataService.overwritePreferences(preferences);
		mockMvc = webAppContextSetup(wac).apply(springSecurity()).build()
	}

	def cleanup() {
		metadataService.removeAllLanguages()
		securityService.removeAllUsers()
	}

	def "logging into the system"() {
		expect: "the login page is accessible"
		mockMvc.perform(get("/login.page").secure(true)).andReturn().getResponse().getStatus() == 200
		
		and: "the login page requires https"
		MockHttpServletResponse loginPageResponse = mockMvc.perform(get("/login.page")).andReturn().getResponse()
		loginPageResponse.getStatus() == HttpServletResponse.SC_FOUND
		loginPageResponse.getRedirectedUrl().startsWith('https://')
		
		when: "an invalid user tries to log in"
		MockHttpServletResponse invalidLoginResponse = mockMvc.perform(post("/login").param("username", "notexistent")
			.param("password", "invalid").secure(true)).andReturn().getResponse()
		
		then: "the request is redirected back to the login page"
		invalidLoginResponse.getStatus() == HttpServletResponse.SC_FOUND
		invalidLoginResponse.getRedirectedUrl().contains('/login.page')

		when: "a valid user tries to log in"
		MvcResult validLoginResult = mockMvc.perform(post("/login").param("username", USER_USERNAME)
			.param("password", USER_PASSWORD).secure(true)).andReturn()
		
		then: "the login is successful and the request is redirected to the index page"
		with(validLoginResult.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_FOUND
			getRedirectedUrl().contains('/index.page')
		}
		SecurityContext sc = validLoginResult.getRequest().getSession().getAttribute(SPRING_SECURITY_CONTEXT_KEY)
		sc.getAuthentication().getName() == USER_USERNAME
	}

}
