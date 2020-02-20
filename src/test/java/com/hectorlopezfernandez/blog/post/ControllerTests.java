package com.hectorlopezfernandez.blog.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class ControllerTests extends BaseMvcTest {

	@Test
	public void testArchiveRoot() throws Exception {
		mockMvc.perform(get("/archive"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

	@Test
	public void testYearly() throws Exception {
		mockMvc.perform(get("/archive/2017"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

	@Test
	public void testMonthly() throws Exception {
		mockMvc.perform(get("/archive/2017/02"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

	@Test
	public void testPermalink() throws Exception {
		mockMvc.perform(get("/archive/2017/02/post-name"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

}