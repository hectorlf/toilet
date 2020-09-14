package com.hectorlopezfernandez.blog.tag

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import com.hectorlopezfernandez.blog.Application
import com.hectorlopezfernandez.blog.TestApplicationPersistence

import spock.lang.Specification

@SpringBootTest(classes=[TestApplicationPersistence.class,Application.class])
class TagRepositorySpecification extends Specification {

	@Autowired
	TagRepository tagRepository

	def "inserting and deleting a tag"() {
		given: "a new tag object"
		def tag = new Tag("tag1", "tag1")
		
		when: "object is saved"
		Tag result = tagRepository.save(tag)
		
		then: "the resulting object has an id"
		result != null
		!result.id?.empty
		
		when: "object is deleted"
		tagRepository.delete(tag)
		
		then: "no exception is thrown"
		notThrown()
	}

	def "listing all tags"() {
		given: "a couple of tags exist in the database"
		def tag1 = tagRepository.save(new Tag("tag1", "tag1"))
		def tag2 = tagRepository.save(new Tag("tag2", "tag2"))
		
		when: "all tags are listed"
		def results = tagRepository.findAll()
		
		then: "the tags are returned"
		results != null
		results.size == 2
		results.contains(tag1)
		results.contains(tag2)
		
		cleanup:
		tagRepository.deleteAll()
	}

	def "finding tags by slug"() {
		given: "a couple of tags exist in the database"
		def tag1 = tagRepository.save(new Tag("tag1", "tag1"))
		def tag2 = tagRepository.save(new Tag("tag2", "tag2"))
		
		when: "an existing tag is searched by slug"
		def result = tagRepository.findBySlug("tag1")
		
		then: "the tag is returned"
		result != null
		result.isPresent()
		result.get().equals(tag1)
		
		when: "a non-existing tag is searched by slug"
		def nonExistent = tagRepository.findBySlug('non-existent')
		
		then: "the tag is not found"
		nonExistent != null
		nonExistent.isEmpty()
		
		cleanup:
		tagRepository.deleteAll()
	}

}
