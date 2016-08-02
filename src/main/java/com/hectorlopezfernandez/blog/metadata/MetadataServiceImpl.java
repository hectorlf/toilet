package com.hectorlopezfernandez.blog.metadata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataServiceImpl implements MetadataService {

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public List<Language> findAllLanguages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Language getDefaultLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

}
