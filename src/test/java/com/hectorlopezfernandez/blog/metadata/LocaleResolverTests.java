package com.hectorlopezfernandez.blog.metadata;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.Test;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class LocaleResolverTests extends BaseMvcTest {

	@Test
	public void testNoLocale() throws Exception {
		mockMvc.perform(get("/index.page").locale(null))
			.andExpect(status().isOk());
	}

	@Test
	public void testAcceptHeaderLocale1() throws Exception {
		// this should kick in as an accept-language locale
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("es-ES")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Inicio")));
	}

	@Test
	public void testAcceptHeaderLocale2() throws Exception {
		// this should kick in as an accept-language locale
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Home")));
	}

	@Test
	public void testAcceptHeaderLocale3() throws Exception {
		mockMvc.perform(get("/index.page").header("Accept-Language","es-ES, es, en"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Inicio")));
	}

	@Test
	public void testAcceptHeaderLocale4() throws Exception {
		mockMvc.perform(get("/index.page").header("Accept-Language","pt-BR, pt, es"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Inicio")));
	}

}