package com.hectorlopezfernandez.blog.tag

import org.springframework.security.core.userdetails.UsernameNotFoundException

import com.hectorlopezfernandez.blog.metadata.MetadataService

import spock.lang.Specification

class TagServiceSpecification extends Specification {

	TagRepository mockedTagRepository = Mock()
	def tagService = new TagService(mockedTagRepository)

	def "adding a tag"() {
		given: "a new tag"
		def tag = new Tag("tag1", "tag1")
		
		when: "a Tag object is saved"
		def result = tagService.addTag(tag)
		
		then: "the right methods are called"
		1 * mockedTagRepository.save(tag) >> tag
		and: "the refreshed Tag object is returned"
		result == tag
	}

	def "getting a tag by id"() {
		when: "a tag is accessed by id"
		tagService.getTag("1")
		
		then: "the right methods are called"
		1 * mockedTagRepository.findOneById("1")
	}

	def "listing all tags"() {
		when: "the list of all tags is queried"
		tagService.listTags()
		
		then: "the right methods are called"
		1 * mockedTagRepository.findAll()
	}

}
