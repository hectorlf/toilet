package com.hectorlopezfernandez.toilet.tag

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import com.hectorlopezfernandez.toilet.Application

import spock.lang.Specification

@SpringBootTest(classes=[Application.class])
class TagRepositorySpecification extends Specification {

	@Autowired
	TagRepository tagRepository

	def cleanup() {
		tagRepository.deleteAll()
	}

	def "inserting and deleting a tag"() {
		given: "a new tag object"
		def tag = new Tag("tag1", "tag1")
		
		when: "object is saved"
		Tag result = tagRepository.save(tag)
		
		then: "the resulting object has an id"
		result != null
		!result.id?.empty
		
		when: "the object is deleted"
		tagRepository.delete(tag)
		
		then: "no exception is thrown"
		and: "the object doesn't exist anymore"
		tagRepository.findById(result.getId()).isEmpty()
	}

	def "listing all tags"() {
		given: "a couple of tags exist in the database"
		def tag1 = tagRepository.save(new Tag("tag1", "tag1"))
		def tag2 = tagRepository.save(new Tag("tag2", "tag2"))
		
		when: "all tags are listed"
		List<Tag> results = tagRepository.findAll()
		
		then: "the tags are returned"
		results != null
		results.size() == 2
		results.containsAll([tag1, tag2])
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
	}

	def "checking the existence of tags by slug"() {
		given: "a couple of tags exist in the database"
		def tag1 = tagRepository.save(new Tag("tag1", "tag1"))
		def tag2 = tagRepository.save(new Tag("tag2", "tag2"))
		
		when: "an existing tag is checked by slug"
		boolean exists = tagRepository.existsBySlug(tag1.getSlug())
		
		then: "true is returned"
		exists
		
		when: "a non-existing tag is checked by slug"
		exists = tagRepository.existsBySlug('non-existent')
		
		then: "false is returned"
		!exists
	}

}
