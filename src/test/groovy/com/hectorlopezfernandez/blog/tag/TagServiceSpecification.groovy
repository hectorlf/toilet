package com.hectorlopezfernandez.blog.tag

import org.springframework.context.ApplicationEventPublisher

import com.hectorlopezfernandez.blog.post.Post
import com.hectorlopezfernandez.blog.post.PostPublicationEvent

import spock.lang.Ignore
import spock.lang.Specification

class TagServiceSpecification extends Specification {

	TagRepository mockedTagRepository = Mock()
	ApplicationEventPublisher eventPublisher = Mock()
	def tagService = new TagService(mockedTagRepository, eventPublisher)

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
		1 * mockedTagRepository.findById("1")
	}

	def "listing tags"() {
		when: "tags are listed"
		def result = tagService.listTags()
		
		then: "the right methods are called"
		1 * mockedTagRepository.findAll() >> []
		and: "a list of Tags is returned"
		result == []
	}

	@Ignore
	def "editing a tag"() {
		given: "an existing tag"
		def tag = new Tag("tag-1", "tag 1")
		
		when: "the Tag object is edited"
		def result = tagService.editTag(tag)
		
		then: "the right methods are called"
		1 * mockedTagRepository.save(tag) >> tag
		and: "the refreshed Tag object is returned"
		result == tag
	}

	@Ignore
	def "deleting a tag"() {
		given: "an existing tag"
		def tag = new Tag("tag-1", "tag 1")
		tag.setId("1")
		
		when: "the Tag object is deleted"
		def result = tagService.editTag(tag)
		
		then: "the right methods are called"
		1 * mockedTagRepository.save(tag)
	}

	@Ignore
	def "updating tag counts"() {
		given: "a post publication event"
		Post post = Mock()
		post.getTags() >> []
		PostPublicationEvent event = Mock()
		event.getType() >> PostPublicationEvent.Type.PUBLISHED
		event.getSource() >> post
		
		when: "the Tag counts are updated"
		tagService.updateTagCounts(event)
		
		then: "the right methods are called"
		1 * mockedTagRepository.updateTagCount("1")
	}

}
