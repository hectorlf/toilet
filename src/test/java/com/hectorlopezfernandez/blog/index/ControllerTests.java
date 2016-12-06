package com.hectorlopezfernandez.blog.index;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class ControllerTests extends BaseMvcTest {

	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/index.page"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

	@Test
	public void testI18n1() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("en")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Home")));
	}

	@Test
	public void testI18n2() throws Exception {
		mockMvc.perform(get("/index.page").locale(Locale.forLanguageTag("es")))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Inicio")));
	}

}