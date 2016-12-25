package com.hectorlopezfernandez.blog.metadata;

import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="languages")
@CompoundIndexes(
	@CompoundIndex(def="{'langCode':1,'regionCode':1}",unique=true)
)
public class Language {

	@Id
	private String id;
	
	// indexed at class level
	private String langCode;
	private String regionCode;

	@Indexed
	private boolean primary;

	// utility methods
	
	public Locale toLocale() {
		if (langCode == null || langCode.isEmpty()) return null;
		if (regionCode == null || regionCode.isEmpty()) return new Locale(langCode);
		return new Locale(langCode, regionCode);
	}

	// getters & setters

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

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

	public boolean isPrimary() {
		return primary;
	}
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

}