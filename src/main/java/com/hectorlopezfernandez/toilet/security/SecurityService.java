package com.hectorlopezfernandez.toilet.security;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

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

	public User addUser(User user) {
		if (user == null) throw new IllegalArgumentException("User argument can't be null");
		return userRepository.save(user);
	}

	public void removeAllUsers() {
		userRepository.deleteAll();
	}

	// Role related methods

	public List<Role> listRoles() {
		return roleRepository.findAll();
	}

	// initialization

	public void initialize() {
		Role adminRole = new Role(WellKnownRoles.ADMIN.name(), WellKnownRoles.ADMIN.description());
		roleRepository.save(adminRole);
		Role userRole = new Role(WellKnownRoles.USER.name(), WellKnownRoles.USER.description());
		roleRepository.save(userRole);

		User superAdmin = new User();
		superAdmin.setEnabled(true);
		superAdmin.setLanguage("en");
		UUID uuid = UUID.randomUUID();
		logger.warn("IMPORTANT! New admin password generated: {}", uuid.toString());
		superAdmin.setPassword(BCrypt.hashpw(uuid.toString(), BCrypt.gensalt()));
		superAdmin.setUsername("admin");
		superAdmin.addRole(WellKnownRoles.ADMIN.name());
		userRepository.save(superAdmin);
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		Role adminRole = new Role(WellKnownRoles.ADMIN.name(), WellKnownRoles.ADMIN.description());
		roleRepository.save(adminRole);
		Role userRole = new Role(WellKnownRoles.USER.name(), WellKnownRoles.USER.description());
		roleRepository.save(userRole);

		User superAdmin = new User();
		superAdmin.setEnabled(true);
		superAdmin.setLanguage("en");
		// sample password is: admin
		superAdmin.setPassword("$2y$10$PZx0mISGuCMAu6vg5fctH.8mDSHSSD2DfH1XBLwWXlPViH3TwsLjK");
		superAdmin.setUsername("admin");
		superAdmin.addRole(WellKnownRoles.ADMIN.name());
		userRepository.save(superAdmin);
	}

}