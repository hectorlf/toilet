package com.hectorlopezfernandez.toilet.post;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hectorlopezfernandez.toilet.author.Author;
import com.hectorlopezfernandez.toilet.author.AuthorService;
import com.hectorlopezfernandez.toilet.metadata.MetadataService;
import com.hectorlopezfernandez.toilet.tag.Tag;
import com.hectorlopezfernandez.toilet.tag.TagsService;

@Service
public class ArchiveService {

	private final PostRepository postRepository;
	private final ArchiveEntryRepository archiveEntryRepository;
	private final AuthorService authorService;
	private final TagsService tagsService;
	private final MetadataService metadataService;

	@Inject
	public ArchiveService(PostRepository postRepository, ArchiveEntryRepository archiveEntryRepository, 
			AuthorService authorService, TagsService tagsService, MetadataService metadataService,
			ApplicationEventPublisher eventPublisher) {
		this.archiveEntryRepository = archiveEntryRepository;
		this.postRepository = postRepository;
		this.authorService = authorService;
		this.tagsService = tagsService;
		this.metadataService = metadataService;
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
	 * Generic list function with paging and sorting
	 */
	public Page<Post> listPosts(PageRequest pageAndOrder) {
		return postRepository.findAll(pageAndOrder);
	}
	
	/**
	 * Returns the list of posts associated with a given tag
	 */
	public List<Post> listPostsByTag(String tag) {
		if (tag == null || tag.isBlank()) return Collections.emptyList();
		return postRepository.findAllByPublishedIsTrueAndTagsIn(tag);
	}
	
	/**
	 * Returns the list of posts associated with a given year
	 */
	public List<Post> listPostsByYear(int year) {
		long lowestMillisForYear = ZonedDateTime.of(year, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant().toEpochMilli();
		long highestMillisForYear = ZonedDateTime.of(year, 12, 31, 23, 59, 59, 999999999, ZoneId.systemDefault()).toInstant().toEpochMilli();
		return postRepository.findPublishedBetween(lowestMillisForYear, highestMillisForYear);
	}

	/**
	 * Returns the list of posts associated with a given year and month
	 */
	public List<Post> listPostsByMonth(int year, int month) {
		if (month < 1 || month > 12) return Collections.emptyList();
		long lowestMillisForMonth = ZonedDateTime.of(year, month, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant().toEpochMilli();
		long highestMillisForMonth = ZonedDateTime.of(year, month, Month.of(month).length(Year.isLeap(year)), 23, 59, 59, 999999999, ZoneId.systemDefault()).toInstant().toEpochMilli();
		return postRepository.findPublishedBetween(lowestMillisForMonth, highestMillisForMonth);
	}

	/**
	 * Returns the Post details identified by the slug
	 */
	public Optional<SinglePostView> getDataForPostPage(int year, int month, String slug) {
		if (slug == null || slug.isEmpty()) return Optional.empty();
		Post post = postRepository.findBySlug(slug);
		if (post == null) return Optional.empty();
		LocalDateTime publicationDate = post.getPublicationTimeAsDate();
		if (publicationDate.getYear() != year || publicationDate.getMonthValue() != month) return Optional.empty();
		//FIXME return something more meaningful than null? an empty-state author?
		Author author = authorService.getAuthor(post.getAuthor()).orElse(null);
		Collection<Tag> tags = tagsService.getTags(post.getTags());
		return Optional.of(new SinglePostView(post, author, tags));
	}

	// archive entries

	/**
	 * Entry list for the archive root
	 */
	public List<ArchiveEntry> listArchiveEntries() {
		return archiveEntryRepository.findAll();
	}

	// sitemap

	/**
	 * Returns a list of posts tailored for the sitemap
	 */
	public List<SitemapPostView> listPostsForSitemap() {
		List<SitemapPostView> posts = postRepository.findAllByPublishedIsTrue().stream()
				.map(element -> new SitemapPostView(element.getSlug(), element.getPublicationTime(),
						element.getLastModificationTime()))
				.collect(Collectors.toList());
		return posts;
	}

	// feeds

	/**
	 * Returns a container of posts tailored for the feed
	 */
	public FeedPostsView listPostsForFeed() {
		long minPostTime = System.currentTimeMillis() - metadataService.getPreferences().getPostAgeLimitForFeed();
		List<Post.FeedProjection> posts = postRepository.findByPublishedIsTrueAndPublicationTimeGreaterThanEqual(minPostTime);
		Set<String> authorIds = posts.stream().map(Post.FeedProjection::getAuthor).collect(Collectors.toSet());
		List<Author.FeedProjection> authors = authorService.getAuthorsForFeed(authorIds);
		return new FeedPostsView(posts, authors);
	}
	
}
