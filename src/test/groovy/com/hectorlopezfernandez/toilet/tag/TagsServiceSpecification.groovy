package com.hectorlopezfernandez.toilet.tag

import spock.lang.Specification

class TagsServiceSpecification extends Specification {

	TagRepository mockedTagRepository = Mock()
	def tagsService = new TagsService(mockedTagRepository)

	def "listing all tags"() {
		when: "the list of all tags is queried"
		tagsService.listTags()
		
		then: "the right methods are called"
		1 * mockedTagRepository.findAll()
	}

	def "get tags using a list of ids"() {
		given: "a collection of ids"
		Collection<String> ids = ['1','2']
		Tag tag1 = new Tag('tag-1', 'tag 1')
		tag1.setId('1')
		Tag tag2 = new Tag('tag-2', 'tag 2')
		tag2.setId('2')
		
		when: "the list of tags corresponding to those ids is queried"
		def results = tagsService.getTags(ids)
		
		then: "the right methods are called"
		1 * mockedTagRepository.findAllById(ids) >> [tag1, tag2]
		and: "a list of Tags is returned"
		results == [tag1, tag2]
	}

	def "get tag by slug"() {
		given: "an existing tag"
		Tag tag1 = new Tag('tag-1', 'tag 1')
		tag1.setId('1')
		
		when: "the tag is queried by slug"
		def result = tagsService.getTagBySlug(tag1.getSlug())
		
		then: "the right methods are called"
		1 * mockedTagRepository.findBySlug(tag1.getSlug()) >> Optional.of(tag1)
		and: "the right tag is returned"
		result == Optional.of(tag1)
	}

	def "get tags using a list of slugs"() {
		given: "a collection of slugs"
		Collection<String> slugs = ['tag-1','tag-2']
		Tag tag1 = new Tag('tag-1', 'tag 1')
		tag1.setId('1')
		Tag tag2 = new Tag('tag-2', 'tag 2')
		tag2.setId('2')
		
		when: "the list of tags corresponding to those slugs is queried"
		def results = tagsService.getTagsBySlug(slugs)
		
		then: "the right methods are called"
		1 * mockedTagRepository.findBySlug(tag1.getSlug()) >> Optional.of(tag1)
		1 * mockedTagRepository.findBySlug(tag2.getSlug()) >> Optional.of(tag2)
		and: "a list of Tags is returned"
		results == [tag1, tag2]
	}

}
