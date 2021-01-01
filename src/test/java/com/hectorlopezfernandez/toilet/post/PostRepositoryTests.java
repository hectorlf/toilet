package com.hectorlopezfernandez.toilet.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.toilet.BaseTest;

public class PostRepositoryTests extends BaseTest {
	
	@Autowired
	private PostRepository postRepository;

	@Test
	public void testPosts() {
		assertTrue(postRepository.findAll().size() == 2);
		Post post = postRepository.findBySlug("title2");
		assertNotNull(post);
		assertEquals("Title2", post.getTitle());
		post = postRepository.findBySlug("nonexistent");
		assertNull(post);
		List<Post.FeedProjection> postList = postRepository.findByPublishedIsTrueAndPublicationTimeGreaterThanEqual(System.currentTimeMillis() - 60000);
		assertNotNull(postList);
		assertTrue(postList.size() == 1);
	}

	@BeforeEach
	public void setup() {
		Post p = new Post();
		p.setContent("Content1");
		p.setCreationTime(System.currentTimeMillis());
		p.setPublicationTime(System.currentTimeMillis());
		p.setTitle("Title1");
		p.setSlug("title1");
		p.setPublished(true);
		postRepository.save(p);
		p = new Post();
		p.setContent("Content2");
		p.setCreationTime(0);
		p.setPublicationTime(0);
		p.setTitle("Title2");
		p.setSlug("title2");
		p.setPublished(false);
		postRepository.save(p);
	}

	@AfterEach
	public void teardown() {
		postRepository.deleteAll();
	}

}