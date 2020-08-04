package com.hectorlopezfernandez.blog.security;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

/**
 * A Role is a simple string that represents a permission
 * applicable to some part of the system, e.g. access to the admin UI.
 */
@Document(collection="roles")
public final class Role implements GrantedAuthority {

	private static final long serialVersionUID = -5992698407860058855L;

	@Id
	private String id;
	private String name;
	private String description;
	
	// constructors
	
	public Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	public Role(String name, String description) {
		this.name = name;
		this.description = description;
	}

	// GrantedAuthority interface

	@Override
	public String getAuthority() {
		return name;
	}

	// getters & setters

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	// equals & hashcode

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Role)) return false;
		return this.name != null && this.name.equals(((Role)other).name);
	}

	@Override
	public int hashCode() {
		return name == null ? 0 : name.hashCode();
	}

}
