package com.hectorlopezfernandez.blog.admin.dashboard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.hectorlopezfernandez.blog.BaseMvcTest;

public class ControllerTests extends BaseMvcTest {

	@Test
	public void testDashboard() throws Exception {
		mockMvc.perform(get("/dashboard.page"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

}