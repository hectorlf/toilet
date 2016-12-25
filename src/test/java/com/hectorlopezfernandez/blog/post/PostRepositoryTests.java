package com.hectorlopezfernandez.blog.post;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class PostRepositoryTests extends BaseTest {
	
	@Autowired
	private PostRepository postRepository;

	@Test
	public void testPosts() {
		Assert.assertTrue(postRepository.findAll().size() == 2);
		List<Post> postList = postRepository.findByTitle("Title2");
		Assert.assertNotNull(postList);
		Assert.assertTrue(postList.size() == 1);
		Assert.assertEquals("Title2", postList.get(0).getTitle());
		postList = postRepository.findByCreationDateLessThanEqual(System.currentTimeMillis() - 60000);
		Assert.assertNotNull(postList);
		Assert.assertTrue(postList.size() == 1);
		postList = postRepository.findByTitle("nonexistent");
		Assert.assertTrue(postList.size() == 0);
	}

	@Before
	public void setup() {
		Post p = new Post();
		p.setContent("Content1");
		p.setCreationDate(System.currentTimeMillis());
		p.setTitle("Title1");
		postRepository.save(p);
		p = new Post();
		p.setContent("Content2");
		p.setCreationDate(Long.valueOf(0));
		p.setTitle("Title2");
		postRepository.save(p);
	}

	@After
	public void teardown() {
		postRepository.deleteAll();
	}

}