package com.hectorlopezfernandez.toilet.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import com.hectorlopezfernandez.toilet.Application

import spock.lang.Specification

@SpringBootTest(classes=[Application.class])
class UserRepositorySpecification extends Specification {

	@Autowired
	UserRepository userRepository

	def "inserting and deleting a user"() {
		given: "a new user object"
		def user = new User('user1')
		
		when: "object is saved"
		def result = userRepository.save(user)
		
		then: "the resulting object has an id"
		result != null
		!result.id?.empty
		
		when: "object is deleted"
		userRepository.delete(user)
		
		then: "no exception is thrown"
		notThrown()
	}

	def "listing all users"() {
		given: "a couple of users exist in the database"
		def user1 = userRepository.save(new User('user1'))
		def user2 = userRepository.save(new User('user2'))
		
		when: "all users are listed"
		def results = userRepository.findAll()
		
		then: "the users are returned"
		results != null
		results.size() == 2
		results.stream().anyMatch { e -> e.username == user1.username }
		results.stream().anyMatch { e -> e.username == user2.username }
		
		cleanup:
		userRepository.deleteAll()
	}

	def "finding users by username"() {
		given: "a couple of users exist in the database"
		def user1 = userRepository.save(new User('user1'))
		def user2 = userRepository.save(new User('user2'))
		
		when: "an existing user is searched by username"
		def result = userRepository.findByUsername('user1')
		
		then: "the user is returned"
		result != null
		result.username == user1.username
		
		when: "a non-existing user is searched by name"
		def nonExistent = userRepository.findByUsername('non-existent')
		
		then: "the user is not found"
		nonExistent == null
		
		cleanup:
		userRepository.deleteAll()
	}

}
