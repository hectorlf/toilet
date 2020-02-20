package com.hectorlopezfernandez.blog.index;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class ControllerTests extends BaseMvcTest {

	@Test
	public void index_statusOkAndContentIsHtml() throws Exception {
		mockMvc.perform(get("/index.page"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

}