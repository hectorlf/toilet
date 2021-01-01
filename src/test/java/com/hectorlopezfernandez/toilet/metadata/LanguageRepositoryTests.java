package com.hectorlopezfernandez.toilet.metadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.toilet.BaseTest;

public class LanguageRepositoryTests extends BaseTest {

	private static final String US_LANGUAGE_TAG = "en-US";

	@Autowired
	private LanguageRepository languageRepository;

	@BeforeEach
	public void setup() {
		Language l = new Language();
		l.setTag("es-ES");
		languageRepository.save(l);
		l = new Language();
		l.setTag(US_LANGUAGE_TAG);
		languageRepository.save(l);
	}

	@AfterEach
	public void teardown() {
		languageRepository.deleteAll();
	}

	@Test
	public void testFindAll_TwoLanguagesReturned() {
		List<Language> languages = languageRepository.findAll();
		assertThat(languages, hasSize(2));
	}

	@Test
	public void testFindAllTag_TwoStringsReturned() {
		List<Language.TagOnly> languageTags = languageRepository.findAllTags();
		assertThat(languageTags, hasSize(2));
	}

	@Test
	public void testFindByTag_EnUs_FoundOk() {
		Language language = languageRepository.findByTag(US_LANGUAGE_TAG);
		assertThat(language, is(notNullValue()));
	}

	@Test
	public void testFindByTag_NonExistingTag_NotFound() {
		Language language = languageRepository.findByTag("madeup");
		assertThat(language, is(nullValue()));
	}

}