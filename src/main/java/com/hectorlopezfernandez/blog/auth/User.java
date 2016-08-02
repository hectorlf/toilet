package com.hectorlopezfernandez.blog.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hectorlopezfernandez.blog.metadata.Language;

/**
 * Application-oriented view of the USERS collection
 * 
 * @author hector
 */
@Document(collection="users")
public class User {

	@Id
	private String id;
	
	@Indexed(unique=true)
	private String username;

	@DBRef
	private Language language;

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

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

}
