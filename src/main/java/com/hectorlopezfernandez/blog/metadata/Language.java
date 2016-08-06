package com.hectorlopezfernandez.blog.metadata;

import java.util.Locale;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="languages")
@CompoundIndexes(
	@CompoundIndex(def="{'langCode':1,'regionCode':1,'variantCode':1}",unique=true)
)
public class Language {

	public static final Long DEFAULT_LANGUAGE_ID = Long.valueOf(1);

	@Id
	private String id;
	
	// indexed at class level
	private String langCode;
	private String regionCode;
	private String variantCode;

	@Indexed
	private boolean defaultLanguage;

	// utility methods
	
	public Locale toLocale() {
		if (langCode == null || langCode.isEmpty()) return null;
		if (regionCode == null || regionCode.isEmpty()) return new Locale(langCode);
		if (variantCode == null || variantCode.isEmpty()) return new Locale(langCode, regionCode);
		return new Locale(langCode, regionCode, variantCode);
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

	public String getVariantCode() {
		return variantCode;
	}
	public void setVariantCode(String variantCode) {
		this.variantCode = variantCode;
	}

	public boolean isDefaultLanguage() {
		return defaultLanguage;
	}
	public void setDefaultLanguage(boolean defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

}
