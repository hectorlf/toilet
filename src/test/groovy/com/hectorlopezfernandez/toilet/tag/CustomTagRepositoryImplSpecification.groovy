package com.hectorlopezfernandez.toilet.tag

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import com.hectorlopezfernandez.toilet.Application
import com.hectorlopezfernandez.toilet.post.Post
import com.hectorlopezfernandez.toilet.post.PostRepository

import spock.lang.Specification

@SpringBootTest(classes=[Application.class])
class CustomTagRepositoryImplSpecification extends Specification {

	@Autowired
	TagRepository tagRepository
	@Autowired
	PostRepository postRepository
	@Autowired
	CustomTagRepositoryImpl customTagRepositoryImpl
	
	Tag tag1
	Tag tag2

	def setup() {
		tag1 = new Tag("tag1", "Tag 1")
		tag1 = tagRepository.save(tag1)
		tag2 = new Tag("tag2", "Tag 2")
		tag2 = tagRepository.save(tag2)
	}

	def cleanup() {
		tagRepository.deleteAll()
		postRepository.deleteAll()
	}

	def "listing tags filtered by parameters"() {
		given: "a set of tags exist in the db"
		
		when: "tags are listed without filters"
		List<Tag> allTags = customTagRepositoryImpl.findTagsFilteredBy(Optional.empty())
		
		then: "all of the tags are returned"
		allTags != null
		allTags.size() == 2
		allTags.containsAll([tag1, tag2])
		
		when: "tags are listed filtered by slug"
		List<Tag> oneTag = customTagRepositoryImpl.findTagsFilteredBy(Optional.of(tag1.getSlug()))
		
		then: "only the right tag is returned"
		oneTag != null
		oneTag.size() == 1
		oneTag.contains(tag1)
	}

	def "updating tag counts"() {
		given: "a set of tag exist in the db"
		and: "posts are tagged with them"
		Post post1 = new Post()
		post1.setSlug("post1")
		post1.setPublished(true)
		post1.setTags([tag1.getId()])
		postRepository.save(post1)
		Post post2 = new Post()
		post2.setSlug("post2")
		post2.setPublished(true)
		postRepository.save(post2)
		Post post3 = new Post()
		post3.setSlug("post3")
		post3.setPublished(false)
		post3.setTags([tag2.getId()])
		postRepository.save(post3)

		
		when: "the tag count is updated for a tag with a published post"
		customTagRepositoryImpl.updateTagCount(tag1.getId())
		
		then: "the right value is stored in the db"
		Optional<Tag> result1 = tagRepository.findById(tag1.getId())
		result1.isPresent()
		result1.get().getCount() == 1
		
		when: "the tag count is updated for a tag with an unpublished post"
		customTagRepositoryImpl.updateTagCount(tag2.getId())
		
		then: "the right value is stored in the db"
		Optional<Tag> result2 = tagRepository.findById(tag2.getId())
		result2.isPresent()
		result2.get().getCount() == 0
	}

}
