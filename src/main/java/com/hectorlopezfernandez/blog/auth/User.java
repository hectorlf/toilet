package com.hectorlopezfernandez.blog.auth;

import java.util.List;
import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Application-oriented view of the USERS collection
 * 
 * @author hector
 */
@Document(collection="users")
public class User implements UserDetails {

	private static final long serialVersionUID = -2219132465446211105L;

	@Id
	private String id;
	@Indexed(unique=true)
	private String username;
	private Language language;

	// security related
	private String password;
	private boolean enabled;
	@DBRef
	private List<Authority> authorities;

	// application related
	private String displayName;
	private String about;
	private String relatedUrl;

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
	
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
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

	// helper classes

	public static class Language {

		private String langCode;
		private String regionCode;

		public Language() {
		}
		public Language(String langCode, String regionCode) {
			this.langCode = langCode;
			this.regionCode = regionCode;
		}

		// utility methods
		
		public Locale toLocale() {
			if (langCode == null || langCode.isEmpty()) return null;
			if (regionCode == null || regionCode.isEmpty()) return new Locale(langCode);
			return new Locale(langCode, regionCode);
		}

		// getters & setters

		public String getLangCode() {
			return langCode;
		}
		public void setLangCode(String langCode) {
			this.langCode = langCode;
		}

		public String getRegionCode() {
			return regionCode;
		}
		public void setRegionCode(String regionCode) {
			this.regionCode = regionCode;
		}

	}

}