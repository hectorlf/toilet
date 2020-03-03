package com.hectorlopezfernandez.blog.post;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ArchiveEntryRepository archiveEntryRepository;

	// posts

	/**
	 * Returns a list of posts tailored for the index page
	 */
	public List<Post> listIndexPosts() {
		Pageable pageable = PageRequest.of(0, 3);
		return postRepository.findByPublishedIsTrueOrderByPublicationDateDesc(pageable).getContent();
	}

	/**
	 * Returns a list of posts tailored for the sitemap
	 */
	public List<Post> listPostsForSitemap() {
		return postRepository.findAllByPublishedIsTrue();
	}

	/**
	 * Generic list function with paging and sorting
	 */
	public Page<Post> listPosts(PageRequest pageAndOrder) {
		return postRepository.findAll(pageAndOrder);
	}

	/**
	 * Returns the Post identified by the id argument
	 */
	public Optional<Post> getPost(String id) {
		return postRepository.findOneById(id);
	}

	// archive entries

	/**
	 * Entry list for the archive root
	 */
	public List<ArchiveEntry> listArchiveEntries() {
		return archiveEntryRepository.findAll();
	}

}
