package com.hectorlopezfernandez.blog.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import com.hectorlopezfernandez.blog.Application

import spock.lang.Specification

@SpringBootTest(classes=[Application.class])
class RoleRepositorySpecification extends Specification {

	@Autowired
	RoleRepository roleRepository

	def "inserting and deleting a role"() {
		given: "a new role object"
		def role = new Role('role1')
		
		when: "object is saved"
		Role result = roleRepository.save(role)
		
		then: "the resulting object has an id"
		result != null
		!result.id?.empty
		
		when: "object is deleted"
		roleRepository.delete(role)
		
		then: "no exception is thrown"
		notThrown()
	}

	def "listing all roles"() {
		given: "a couple of roles exist in the database"
		def role1 = roleRepository.save(new Role('role1'))
		def role2 = roleRepository.save(new Role('role2'))
		
		when: "all roles are listed"
		def results = roleRepository.findAll()
		
		then: "the roles are returned"
		results != null
		results.size == 2
		results.contains(role1)
		results.contains(role2)
		
		cleanup:
		roleRepository.deleteAll()
	}

	def "finding roles by name"() {
		given: "a couple of roles exist in the database"
		def role1 = roleRepository.save(new Role('role1'))
		def role2 = roleRepository.save(new Role('role2'))
		
		when: "an existing role is searched by name"
		def result = roleRepository.findByName('role1')
		
		then: "the role is returned"
		result != null
		result.equals(role1)
		
		when: "a non-existing role is searched by name"
		def nonExistent = roleRepository.findByName('non-existent')
		
		then: "the role is not found"
		nonExistent == null
		
		cleanup:
		roleRepository.deleteAll()
	}

}
