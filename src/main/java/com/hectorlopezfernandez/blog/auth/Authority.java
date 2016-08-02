package com.hectorlopezfernandez.blog.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection="authorities")
public final class Authority implements GrantedAuthority {

	private static final long serialVersionUID = -5992698407860058855L;

	@Id
	private String id;
	
	@Indexed(unique=true)
	private String authority;

	// getters & setters

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	// equals & hashcode

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof Authority)) return false;
		Authority otherAuthority = (Authority)other;
		return this.authority.equals(otherAuthority.authority);
	}

	@Override
	public int hashCode() {
		return 17 * this.authority.hashCode();
	}

}
