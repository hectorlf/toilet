package com.hectorlopezfernandez.toilet.post;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.toilet.BaseTest;
import com.hectorlopezfernandez.toilet.PaginationData;

public class ArchiveServiceTests extends BaseTest {

	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private PostRepository postRepository;

	@Test
	public void testListIndexPosts_PaginationEnabled() {
		PaginationData pagination = new PaginationData();
		List<Post> posts = archiveService.listIndexPosts(pagination);
		Assertions.assertNotNull(posts);
		Assertions.assertTrue(posts.size() > 0);
	}

	@Test
	public void testListIndexPosts_PaginationDisabled() {
		PaginationData pagination = new PaginationData().setEnabled(false);
		List<Post> posts = archiveService.listIndexPosts(pagination);
		Assertions.assertNotNull(posts);
		Assertions.assertTrue(posts.size() > 0);
	}

	@BeforeEach
	public void setup() {
		Post p = new Post();
		p.setTitle("Test");
		p.setPublished(true);
		postRepository.save(p);
	}

	@AfterEach
	public void teardown() {
		postRepository.deleteAll();
	}

}
