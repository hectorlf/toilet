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

}
