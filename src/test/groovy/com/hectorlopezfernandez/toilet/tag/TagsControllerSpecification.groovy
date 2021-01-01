package com.hectorlopezfernandez.toilet.tag

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.web.context.WebApplicationContext

import com.hectorlopezfernandez.toilet.Application
import com.hectorlopezfernandez.toilet.metadata.Language
import com.hectorlopezfernandez.toilet.metadata.MetadataService
import com.hectorlopezfernandez.toilet.metadata.Preferences

import spock.lang.Ignore
import spock.lang.Specification

@SpringBootTest(classes=[Application.class], webEnvironment=WebEnvironment.RANDOM_PORT)
class TagsControllerSpecification extends Specification {

	@Autowired
	MetadataService metadataService

	@Autowired
	TagService tagService

	@Autowired
	WebApplicationContext wac

	MockMvc mockMvc
	
	def setup() {
		Language spanish = new Language('es');
		metadataService.addLanguage(spanish);
		Preferences preferences = new Preferences();
		preferences.setDefaultLanguage('es');
		metadataService.overwritePreferences(preferences);
		mockMvc = webAppContextSetup(wac).apply(springSecurity()).build()
	}

	def cleanup() {
		metadataService.removeAllLanguages()
	}

	@Ignore("pebble needs to be configured")
	def "accessing the /tags url"() {
		when: "the /tags url is requested"
		MvcResult tagsResult = mockMvc.perform(get("/tags")).andReturn()
		
		then: "the tags page is loaded"
		with(tagsResult.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_OK
			contentType() == "text/html"
		}
	}

	@Ignore("pebble needs to be configured")
	def "accessing the /tags/{tag} url"() {
		when: "the /tags/{tag} url is requested"
		MvcResult tagsResult = mockMvc.perform(get("/tags/tag1")).andReturn()
		
		then: "the tags page is loaded"
		with(tagsResult.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_OK
			contentType() == "text/html"
		}
	}

}
