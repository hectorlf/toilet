package com.hectorlopezfernandez.blog.security;

/**
 * Collection of well known roles
 */
public enum WellKnownRoles {
	ADMIN("The super user role"),
	USER("A regular user");
	
	private final String description;

	private WellKnownRoles(String description) {
		this.description = description;
	}

	public String description() {
		return description;
	}

}
