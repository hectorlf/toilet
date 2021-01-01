package com.hectorlopezfernandez.toilet.index;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import com.hectorlopezfernandez.toilet.BaseMvcTest;

public class IndexControllerTest extends BaseMvcTest {

	@Test
	public void index_statusOkAndContentIsHtml() throws Exception {
		mockMvc.perform(get("/index.page"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
	}

	@Disabled
	@Test
	public void requiresAuthentication() throws Exception {
		mockMvc.perform(get("/admin/index.page").secure(true))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("**/login.page*"))
			.andReturn();
	}

	@Disabled
	@Test
	public void requiresSecure() throws Exception {
		mockMvc.perform(get("/admin/index.page"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrlPattern("https://**"))
			.andReturn();
	}

	// Ignored due to a race condition between @WithUserDetails and @Before
	@Disabled
	@Test
	@WithUserDetails("admin")
	public void accessGranted() throws Exception {
		this.mockMvc.perform(get("/admin/index.page").secure(true))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("ROLE_ADMIN")));
	}

	// Ignored due to a race condition between @WithUserDetails and @Before
	@Disabled
	@Test
	@WithUserDetails("user")
	public void accessDenied() throws Exception {
		this.mockMvc.perform(get("/admin/index.page").secure(true))
			.andExpect(status().isForbidden());
	}

}