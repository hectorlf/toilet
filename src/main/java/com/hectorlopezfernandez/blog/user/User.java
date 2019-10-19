package com.hectorlopezfernandez.blog.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A User is both an author of content and a security artifact,
 * and stores information about the two roles, e.g. password and
 * display name.
 */
@Document(collection="users")
public class User implements UserDetails {

	private static final long serialVersionUID = -2219132465446211105L;

	@Id
	private String id;
	@Indexed(unique=true)
	private String username;
	private String language;

	// security related
	private String password;
	private boolean enabled;
	private Set<String> authorities;

	// application related
	private String displayName;
	private String about;
	private String relatedUrl;

	public User() {
		this.authorities = new HashSet<>();
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

	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	public String getRelatedUrl() {
		return relatedUrl;
	}
	public void setRelatedUrl(String relatedUrl) {
		this.relatedUrl = relatedUrl;
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

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
	/**
	 * Adds an authority string to the set of granted authorities.
	 * 
	 * @param authority  the authority string to add, can't be null or empty
	 * @return  this User object, to allow chained calls
	 */
	public User addAuthority(String authority) {
		if (authority == null || authority.isEmpty()) throw new IllegalArgumentException("Authority argument can't be null or empty");
		this.authorities.add(authority);
		return this;
	}

	// UserDetails getters
	
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
		Collection<Authority> grantedAuthorities = this.authorities.stream()
				.map(authorityString -> new Authority(authorityString)).collect(Collectors.toList());
		return grantedAuthorities;
	}

}