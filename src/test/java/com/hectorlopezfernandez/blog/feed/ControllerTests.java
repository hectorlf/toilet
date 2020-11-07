package com.hectorlopezfernandez.blog.feed;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class ControllerTests extends BaseMvcTest {

	@Test
	public void testFeed() throws Exception {
		mockMvc.perform(get("/feed.atom"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_ATOM_XML_VALUE));
	}

}
