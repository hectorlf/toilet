package com.hectorlopezfernandez.blog.post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hectorlopezfernandez.blog.author.Author;
import com.hectorlopezfernandez.blog.author.AuthorService;
import com.hectorlopezfernandez.blog.tag.Tag;
import com.hectorlopezfernandez.blog.tag.TagsService;

@Service
public class ArchiveService {

	private final PostRepository postRepository;
	private final ArchiveEntryRepository archiveEntryRepository;
	private final AuthorService authorService;
	private final TagsService tagsService;

	@Inject
	public ArchiveService(PostRepository postRepository, ArchiveEntryRepository archiveEntryRepository, 
			AuthorService authorService, TagsService tagsService, ApplicationEventPublisher eventPublisher) {
		this.archiveEntryRepository = archiveEntryRepository;
		this.postRepository = postRepository;
		this.authorService = authorService;
		this.tagsService = tagsService;
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
	 * Returns the Post details identified by the slug
	 */
	public Optional<SinglePostView> getDataForPostPage(Integer year, Integer month, String slug) {
		if (year == null || month == null || slug == null || slug.isEmpty()) return Optional.empty();
		Post post = postRepository.findBySlug(slug);
		if (post == null) return Optional.empty();
		LocalDateTime publicationDate = post.getPublicationTimeAsDate();
		if (publicationDate.getYear() != year || publicationDate.getMonthValue() != month) return Optional.empty();
		Author author = authorService.getAuthorBySlug(post.getAuthor()).orElse(null);
		Collection<Tag> tags = tagsService.getTagsBySlug(post.getTags());
		return Optional.of(new SinglePostView(post, author, tags));
	}

	// archive entries

	/**
	 * Entry list for the archive root
	 */
	public List<ArchiveEntry> listArchiveEntries() {
		return archiveEntryRepository.findAll();
	}

}
