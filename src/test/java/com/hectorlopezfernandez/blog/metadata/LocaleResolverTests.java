package com.hectorlopezfernandez.blog.metadata;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.Ignore;
import org.junit.Test;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class LocaleResolverTests extends BaseMvcTest {

	@Test
	public void noLocaleSelected_indexOk() throws Exception {
		mockMvc.perform(get("/index.page"))
			.andExpect(status().isOk());
	}

	@Test
	public void i18n_localeHasNoSupportedTranslation_pageContainsDefaults() throws Exception {
		// spring boot's i18n will revert to the system's default locale if a key is not found
		// before the base bundle is used
		// that behavior breaks this test when the default locale is either english or spanish,
		// so it has to be faked
		Locale defaultLocale = Locale.getDefault();
		Locale.setDefault(Locale.forLanguageTag("pt"));
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("pt")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("DefaultHome")));
		Locale.setDefault(defaultLocale);
	}

	@Test
	public void i18n_localeIsEnglish_pageContainsEnglish() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("TestHome")));
	}

	@Test
	public void i18n_localeIsSpanish_pageContainsSpanish() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("es")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("TestInicio")));
	}

	@Test
	public void i18n_localeIsSpanishFromSpain_genericSpanishBundleUsed() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("es-ES")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("TestInicio")));
	}

	// multiple accept-languages are not supported by MockHttpServletRequestBuilder
	@Test
	@Ignore
	public void multipleAcceptLanguage_spanishGoesBeforeEnglish_spanishIsSelected() throws Exception {
		mockMvc.perform(get("/index.page").header("Accept-Language","es-ES, es"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("TestInicio")));
	}

	// multiple accept-languages are not supported by MockHttpServletRequestBuilder
	@Test
	@Ignore
	public void multipleAcceptLanguage_usupportedLanguagesBeforeSpanish_spanishIsSelected() throws Exception {
		mockMvc.perform(get("/index.page").header("Accept-Language","de, fr, es"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("TestInicio")));
	}

}