package com.hectorlopezfernandez.toilet.metadata;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import com.hectorlopezfernandez.toilet.BaseSecurityTest;

public class ControllerTests extends BaseSecurityTest {

	// Ignored due to a race condition between @WithUserDetails and @Before
	@Disabled
	@Test
	@WithUserDetails("admin")
	public void testSessionLocale() throws Exception {
		mockMvc.perform(get("/index.page").secure(true))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Bienvenido")));
	}

}