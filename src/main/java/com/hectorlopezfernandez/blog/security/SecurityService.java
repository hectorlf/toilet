package com.hectorlopezfernandez.blog.security;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	@Inject
	public SecurityService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	// UserDetailsService interface
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Loading user by username: {}", username);
		if (username == null || username.isEmpty()) throw new UsernameNotFoundException("Empty username");
		UserDetails u = userRepository.findByUsername(username);
		if (u == null) throw new UsernameNotFoundException("Username '" + username + "' not found");
		return u;
	}

	// User related methods

	public void addUser(User user) {
		if (user == null) throw new IllegalArgumentException("User argument can't be null");
		userRepository.save(user);
	}

	public void removeAllUsers() {
		userRepository.deleteAll();
	}

	// Role related methods

	public List<Role> listRoles() {
		return roleRepository.findAll();
	}

}