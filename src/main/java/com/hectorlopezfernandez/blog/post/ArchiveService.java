package com.hectorlopezfernandez.blog.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ArchiveService {

	// posts

	/**
	 * Returns a list of posts tailored for the index page
	 */
	List<Post> listIndexPosts();

	/**
	 * Returns a list of posts tailored for the sitemap
	 */
	List<Post> listPostsForSitemap();

	/**
	 * Generic list function with paging and sorting
	 */
	Page<Post> listPosts(PageRequest pageAndOrder);

	/**
	 * Returns the Post identified by the id argument
	 */
	Optional<Post> getPost(String id);

}
