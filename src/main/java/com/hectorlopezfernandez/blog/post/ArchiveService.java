package com.hectorlopezfernandez.blog.post;

import java.util.List;

public interface ArchiveService {

	// posts

	/**
	 * Returns a list of posts tailored for the index page
	 */
	List<Post> listIndexPosts();

}
