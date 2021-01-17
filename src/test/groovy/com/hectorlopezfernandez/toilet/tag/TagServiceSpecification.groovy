package com.hectorlopezfernandez.toilet.tag

import org.springframework.context.ApplicationEventPublisher

import com.hectorlopezfernandez.toilet.post.Post
import com.hectorlopezfernandez.toilet.post.PostPublicationEvent

import spock.lang.Specification

class TagServiceSpecification extends Specification {

	TagRepository mockedTagRepository = Mock()
	ApplicationEventPublisher mockedEventPublisher = Mock()
	TagService tagService = new TagService(mockedTagRepository, mockedEventPublisher)

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

	def "listing all tags"() {
		when: "tags are listed"
		def result = tagService.listTags([:])
		
		then: "the right methods are called"
		1 * mockedTagRepository.findAll() >> []
		and: "a list of Tags is returned"
		result == []
		
		when: "tags are filtered by slug"
		result = tagService.listTags(['slug': 'tag1'])
		
		then: "the right methods are called"
		1 * mockedTagRepository.findTagsFilteredBy(Optional.of('tag1')) >> []
		and: "a list of Tags is returned"
		result == []
	}

	def "editing a tag"() {
		given: "an existing tag"
		Tag tag = new Tag("tag-1", "tag 1")
		tag.setId("1")
		
		when: "the Tag object is edited"
		def result = tagService.editTag(tag)
		
		then: "the right methods are called"
		1 * mockedTagRepository.findById(tag.getId()) >> Optional.of(tag)
		(0..1) * mockedTagRepository.existsBySlug(tag.getSlug()) >> true
		1 * mockedTagRepository.save(tag) >> tag
		1 * mockedEventPublisher.publishEvent(_)
		and: "the refreshed Tag object is returned"
		result == tag
	}

	def "deleting a tag"() {
		given: "an existing tag"
		def tag = new Tag("tag-1", "tag 1")
		tag.setId("1")
		
		when: "the Tag object is deleted"
		tagService.deleteTag(tag.getId())
		
		then: "the right methods are called"
		1 * mockedTagRepository.findById(tag.getId()) >> Optional.of(tag)
		1 * mockedTagRepository.deleteById(tag.getId())
		1 * mockedEventPublisher.publishEvent(_)
	}

	def "updating tag counts"() {
		given: "a post publication event"
		Post post = Mock()
		post.getTags() >> [ "1" ]
		PostPublicationEvent event = Mock()
		event.getType() >> PostPublicationEvent.Type.PUBLISHED
		event.getSource() >> post
		
		when: "the Tag counts are updated"
		tagService.updateTagCounts(event)
		
		then: "the right methods are called"
		1 * mockedTagRepository.updateTagCount("1")
	}

}
