package com.hectorlopezfernandez.toilet.security

import org.springframework.security.core.userdetails.UsernameNotFoundException

import spock.lang.Specification

class SecurityServiceSpecification extends Specification {

	RoleRepository mockedRoleRepository = Mock()
	UserRepository mockedUserRepository = Mock()
	def securityService = new SecurityService(mockedUserRepository, mockedRoleRepository)

	def "searching users with the spring security interfaces"() {
		when: "an existing user is loaded by username"
		def result = securityService.loadUserByUsername('existing')
		
		then: "the right methods are called"
		1 * mockedUserRepository.findByUsername('existing') >> new User('existing')
		
		and: "the existing user is returned"
		result != null
		result.username == 'existing'
		
		when: "a non-existing user is loaded by username"
		def nonExisting = securityService.loadUserByUsername('non-existing')
		
		then: "the right methods are called"
		1 * mockedUserRepository.findByUsername('non-existing')
		
		and: "an exception is thrown"
		thrown(UsernameNotFoundException)
	}

	def "adding a user"() {
		given:
		def user = new User()
		
		when: "a User object is saved"
		securityService.addUser(user)
		
		then: "the right methods are called"
		1 * mockedUserRepository.save(user)
	}

	def "deleting all users"() {
		when: "all the users are deleted"
		securityService.removeAllUsers()
		
		then: "the right methods are called"
		1 * mockedUserRepository.deleteAll()
	}

	def "listing all roles"() {
		when: "the list of all roles is queried"
		securityService.listRoles()
		
		then: "the right methods are called"
		1 * mockedRoleRepository.findAll()
	}

}
