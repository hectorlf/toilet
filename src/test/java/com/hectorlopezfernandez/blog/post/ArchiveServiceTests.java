package com.hectorlopezfernandez.blog.post;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class ArchiveServiceTests extends BaseTest {

	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private PostRepository postRepository;

	@Test
	public void testIndexPostList() {
		List<Post> posts = archiveService.listIndexPosts();
		Assert.assertNotNull(posts);
		Assert.assertTrue(posts.size() > 0);
	}

	@Before
	public void setup() {
		Post p = new Post();
		p.setTitle("Test");
		p.setPublished(true);
		postRepository.save(p);
	}

	@After
	public void teardown() {
		postRepository.deleteAll();
	}

}
