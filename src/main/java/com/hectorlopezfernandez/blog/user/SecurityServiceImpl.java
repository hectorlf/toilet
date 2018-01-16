package com.hectorlopezfernandez.blog.user;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
	
	private UserRepository userRepository;

	@Inject
	public SecurityServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || username.isEmpty()) throw new UsernameNotFoundException("Empty username");
		UserDetails u = userRepository.findByUsername(username);
		if (u == null) throw new UsernameNotFoundException("Username '" + username + "' not found");
		return u;
	}

	@Override
	public void addUser(User user) {
		if (user == null) throw new IllegalArgumentException("User argument can't be null");
		userRepository.save(user);
	}

	@Override
	public void removeAllUsers() {
		userRepository.deleteAll();
	}

}