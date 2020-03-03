package com.hectorlopezfernandez.blog.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class PostRepositoryTests extends BaseTest {
	
	@Autowired
	private PostRepository postRepository;

	@Test
	public void testPosts() {
		assertTrue(postRepository.findAll().size() == 2);
		List<Post> postList = postRepository.findByTitle("Title2");
		assertNotNull(postList);
		assertTrue(postList.size() == 1);
		assertEquals("Title2", postList.get(0).getTitle());
		postList = postRepository.findByCreationDateLessThanEqual(System.currentTimeMillis() - 60000);
		assertNotNull(postList);
		assertTrue(postList.size() == 1);
		postList = postRepository.findByTitle("nonexistent");
		assertTrue(postList.size() == 0);
	}

	@BeforeEach
	public void setup() {
		Post p = new Post();
		p.setContent("Content1");
		p.setCreationDate(System.currentTimeMillis());
		p.setTitle("Title1");
		p.setSlug("title1");
		postRepository.save(p);
		p = new Post();
		p.setContent("Content2");
		p.setCreationDate(Long.valueOf(0));
		p.setTitle("Title2");
		p.setSlug("title2");
		postRepository.save(p);
	}

	@AfterEach
	public void teardown() {
		postRepository.deleteAll();
	}

}