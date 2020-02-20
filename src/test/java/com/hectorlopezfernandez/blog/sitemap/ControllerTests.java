package com.hectorlopezfernandez.blog.sitemap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class ControllerTests extends BaseMvcTest {

	@Test
	public void testSitemap() throws Exception {
		mockMvc.perform(get("/sitemap.xml"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_XML));
	}

}
