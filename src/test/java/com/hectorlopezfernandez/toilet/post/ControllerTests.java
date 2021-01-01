package com.hectorlopezfernandez.toilet.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.hectorlopezfernandez.toilet.BaseMvcTest;
import com.hectorlopezfernandez.toilet.author.Author;
import com.hectorlopezfernandez.toilet.author.AuthorRepository;

public class ControllerTests extends BaseMvcTest {

	@Inject
	private PostRepository postRepository;
	@Inject
	private AuthorRepository authorRepository;

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

	@BeforeEach
	public void setup() {
		super.setup();
		Author author = new Author();
		author.setId("1");
		authorRepository.save(author);
		Post post = new Post();
		post.setPublicationTime(LocalDateTime.of(2017, 2, 1, 0, 0).toInstant(ZoneOffset.UTC).toEpochMilli());
		post.setPublished(true);
		post.setSlug("post-name");
		post.setAuthor("1");
		postRepository.save(post);
	}

	@AfterEach
	public void tearDown() {
		postRepository.deleteAll();
		authorRepository.deleteAll();
		super.tearDown();
	}

}