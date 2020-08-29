package com.hectorlopezfernandez.blog.post;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService {

	private final PostRepository postRepository;
	private final ArchiveEntryRepository archiveEntryRepository;

	@Inject
	public ArchiveService(PostRepository postRepository, ArchiveEntryRepository archiveEntryRepository) {
		this.archiveEntryRepository = archiveEntryRepository;
		this.postRepository = postRepository;
	}

	// posts

	/**
	 * Returns a list of posts tailored for the index page
	 */
	public List<Post> listIndexPosts() {
		Pageable pageable = PageRequest.of(0, 3);
		return postRepository.findByPublishedIsTrueOrderByPublicationTimeDesc(pageable).getContent();
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
	 * Returns the list of posts associated with a given tag
	 */
	public List<Post> listPostsByTag(String tag) {
		//FIXME
		return postRepository.findAll();
	}

	/**
	 * Returns the Post identified by the slug
	 */
	public Optional<Post> getPostBySlug(String slug) {
		return Optional.ofNullable(postRepository.findBySlug(slug));
	}

	// archive entries

	/**
	 * Entry list for the archive root
	 */
	public List<ArchiveEntry> listArchiveEntries() {
		return archiveEntryRepository.findAll();
	}

}
