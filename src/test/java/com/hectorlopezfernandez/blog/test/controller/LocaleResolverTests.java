package com.hectorlopezfernandez.blog.test.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.Test;

import com.hectorlopezfernandez.blog.test.BaseMvcTest;

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
			.andExpect(content().string(containsString("Bienvenido")));
	}

	@Test
	public void testAcceptHeaderLocale2() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en-GB")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testAcceptHeaderLocale3() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en-US")).header("Accept-Language","en-US, en-GB, en"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testAcceptHeaderLocale4() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("pt-BR")).header("Accept-Language","pt-BR, pt, en"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

	@Test
	public void testAcceptHeaderLocale5() throws Exception {
		// this should kick in as an accept-language locale
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en-GB")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Welcome")));
	}

}