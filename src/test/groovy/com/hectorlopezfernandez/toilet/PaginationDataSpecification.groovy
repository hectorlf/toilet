package com.hectorlopezfernandez.toilet

import spock.lang.Specification

class PaginationDataSpecification extends Specification {

	def "empty pagination data has to be page 1"() {		
		when: "an empty PaginationData is created"
		PaginationData paginationData = new PaginationData();
		
		then: "page has to be 1"
		paginationData.getPage() == 1
		paginationData.getEffectivePage() == 0
		paginationData.onFirstPage()
		paginationData.onLastPage()
	}

	def "a pagination of size 1 with 1 element should have page 1"() {
		when: "a PaginationData with 1 element and size 1 is created"
		PaginationData paginationData = new PaginationData(1, 1, 1);
		
		then: "page has to be 1"
		paginationData.getPage() == 1
		paginationData.getEffectivePage() == 0
		paginationData.onFirstPage()
		paginationData.onLastPage()
	}

	def "a pagination of size 10 with 20 elements should have 2 pages"() {
		when: "a PaginationData with 20 elements and size 10 is created"
		PaginationData page1 = new PaginationData(1, 10, 20);
		PaginationData page2 = page1.next()
		
		then: "page 1 has to exist"
		page1.getPage() == 1
		page1.getEffectivePage() == 0
		page1.onFirstPage()
		!page1.onLastPage()
		
		and: "page 2 has to exist"
		page2.getPage() == 2
		page2.getEffectivePage() == 1
		!page2.onFirstPage()
		page2.onLastPage()
	}

	def "a pagination of size 10 with 23 elements should have 3 pages"() {
		when: "a PaginationData with 23 elements and size 10 is created"
		PaginationData page1 = new PaginationData(1, 10, 23);
		PaginationData page2 = page1.next()
		PaginationData page3 = page2.next()
		
		then: "page 1 has to exist"
		page1.getPage() == 1
		page1.getEffectivePage() == 0
		page1.onFirstPage()
		!page1.onLastPage()
		
		
		and: "page 2 has to exist"
		page2.getPage() == 2
		page2.getEffectivePage() == 1
		!page2.onFirstPage()
		!page2.onLastPage()

		and: "page 3 has to exist"
		page3.getPage() == 3
		page3.getEffectivePage() == 2
		!page3.onFirstPage()
		page3.onLastPage()
	}

	def "a page request greater than the available pages should return a valid value"() {
		when: "a PaginationData for page 2 with 10 elements and size 10 is created"
		PaginationData page1 = new PaginationData(2, 10, 10)
		
		then: "the effective page should be 0"
		page1.getEffectivePage() == 0
	}

}
