package com.hectorlopezfernandez.toilet.tag

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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

import spock.lang.Specification

@SpringBootTest(classes=[Application.class], webEnvironment=WebEnvironment.RANDOM_PORT)
class TagsApiControllerSpecification extends Specification {

	@Autowired
	TagRepository tagRepository
	@Autowired
	WebApplicationContext wac

	MockMvc mockMvc
	
	def setup() {
		Tag tag1 = new Tag('tag1', 'tag 1')
		tag1.setId('1')
		tagRepository.save(tag1)
		Tag tag2 = new Tag('tag2', 'tag 2')
		tag2.setId('2')
		tagRepository.save(tag2)
		mockMvc = webAppContextSetup(wac).build()
	}

	def cleanup() {
		tagRepository.deleteAll()
	}

	def "listing tags"() {
		when: "a get to /admin/api/tags is requested"
		MvcResult result = mockMvc.perform(get("/admin/api/tags")).andReturn()

		then: "a list of tags is returned"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_OK
			getContentType() == APPLICATION_JSON_VALUE
			getContentAsString().contains("_embedded")
			getContentAsString().contains("tag1")
			getContentAsString().contains("tag2")
		}
		
		when: "a get to /admin/api/tags?slug={filter} is requested"
		result = mockMvc.perform(get("/admin/api/tags?slug=tag1")).andReturn()

		then: "only the specified tag is returned"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_OK
			getContentType() == APPLICATION_JSON_VALUE
			getContentAsString().contains("_embedded")
			getContentAsString().contains("tag1")
			!getContentAsString().contains("tag2")
		}
	}

	def "retrieving a tag"() {
		when: "a get to /admin/api/tags/{id} is requested"
		MvcResult result = mockMvc.perform(get("/admin/api/tags/1")).andReturn()

		then: "the right tag is returned"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_OK
			getContentType() == APPLICATION_JSON_VALUE
			getContentAsString().contains("tag1")
			!getContentAsString().contains("_embedded")
			!getContentAsString().contains("tag2")
		}
	}

	def "creating a tag"() {
		when: "a post to /admin/api/tags is requested"
		MvcResult result = mockMvc.perform(post("/admin/api/tags")
			.content('{"slug":"test","name":"test"}').contentType(APPLICATION_JSON_VALUE)).andReturn()

		then: "a tag is created"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_CREATED
			getContentType() == APPLICATION_JSON_VALUE
			getHeader('Location').contains('/tags/')
		}
		
		when: "a post to /admin/api/tags is requested and the body contains an id"
		result = mockMvc.perform(post("/admin/api/tags")
			.content('{"id":"test","slug":"test","name":"test"}').contentType(APPLICATION_JSON_VALUE)).andReturn()

		then: "the request is rejected"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_BAD_REQUEST
		}
	}

	def "updating a tag by id"() {
		when: "a put to /admin/api/tags/{id} is requested"
		MvcResult result = mockMvc.perform(put("/admin/api/tags/1")
			.content('{"slug":"tag1","name":"tag 1"}').contentType(APPLICATION_JSON_VALUE)).andReturn()

		then: "the tag is updated"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_CREATED
			getContentType() == APPLICATION_JSON_VALUE
			getHeader('Location').contains('/tags/1')
		}
		
		when: "a put to /admin/api/tags/{id} with a non-existent id is requested"
		result = mockMvc.perform(put("/admin/api/tags/non-existent")
			.content('{"slug":"tag1","name":"tag 1"}').contentType(APPLICATION_JSON_VALUE)).andReturn()

		then: "a 404 response is returned"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_NOT_FOUND
		}
	}

	def "deleting a tag by id"() {
		when: "a delete to /admin/api/tags/{id} is requested"
		MvcResult result = mockMvc.perform(delete("/admin/api/tags/1")).andReturn()
		
		then: "the tag is deleted"
		with(result.getResponse(), MockHttpServletResponse) {
			getStatus() == HttpServletResponse.SC_NO_CONTENT
		}
	}

}
