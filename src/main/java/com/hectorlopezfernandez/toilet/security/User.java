package com.hectorlopezfernandez.toilet.security;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A User is a security artifact that allows for session login,
 * and stores information about the roles and credentials
 */
@Document(collection="users")
public class User implements UserDetails {

	private static final long serialVersionUID = -2219132465446211105L;

	@Id
	private String id;
	private String username;
	private String password;
	private String language;
	private boolean enabled;
	private Set<String> roles;

	// constructors
	
	public User() {
	}

	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String password, String language, boolean enabled) {
		this.username = username;
		this.password = password;
		this.language = language;
		this.enabled = enabled;
	}
	
	// UserDetails interface

	@Override
	public boolean isAccountNonExpired() {
		return enabled;
	}
	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return enabled;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<Role> grantedAuthorities = Collections.emptyList();
		if (roles != null && !roles.isEmpty()) {
			grantedAuthorities = roles.stream().map(authorityString -> new Role(authorityString)).collect(Collectors.toList());
		}
		return grantedAuthorities;
	}

	// getters & setters
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	/**
	 * Adds an authority string to the set of granted authorities.
	 * 
	 * @param authority  the authority string to add, can't be null or empty
	 * @return this User object, to allow chained calls
	 */
	public User addRole(String role) {
		if (role == null || role.isEmpty()) throw new IllegalArgumentException("Role argument can't be null or empty");
		if (roles == null) roles = new HashSet<>();
		roles.add(role);
		return this;
	}
	/**
	 * Adds a collection of roles to the set of granted authorities.
	 * 
	 * @param roles  the collection of roles to add, nulls and empty collections are ignored
	 * @return this User object, to allow chained calls
	 */
	public User addRoles(Collection<String> roles) {
		if (roles != null && !roles.isEmpty()) {
			if (this.roles == null) this.roles = new HashSet<>();
			this.roles.addAll(roles);
		}
		return this;
	}
	/**
	 * Removes an authority string from the set of granted authorities.
	 * 
	 * @param authority  the authority string to remove, can't be null or empty
	 * @return this User object, to allow chained calls
	 */
	public User removeRole(String role) {
		if (role == null || role.isEmpty()) throw new IllegalArgumentException("Role argument can't be null or empty");
		if (roles == null) roles = new HashSet<>();
		roles.remove(role);
		return this;
	}
	/**
	 * Removes a collection of roles from the set of granted authorities.
	 * 
	 * @param roles  the collection of roles to remove, nulls and empty collections are ignored
	 * @return this User object, to allow chained calls
	 */
	public User removeRoles(Collection<String> roles) {
		if (roles != null && !roles.isEmpty()) {
			if (this.roles == null) this.roles = new HashSet<>();
			this.roles.removeAll(roles);
		}
		return this;
	}

}