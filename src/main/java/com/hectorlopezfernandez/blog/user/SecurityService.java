package com.hectorlopezfernandez.blog.user;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

	void addUser(User user);

	void removeAllUsers();

}
