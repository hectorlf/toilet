package com.hectorlopezfernandez.blog.post;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import javax.inject.Inject;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final ArchiveEntryRepository archiveEntryRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Inject
	public PostService(PostRepository postRepository, ArchiveEntryRepository archiveEntryRepository, ApplicationEventPublisher eventPublisher) {
		this.postRepository = postRepository;
		this.archiveEntryRepository = archiveEntryRepository;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * Creates a Post
	 */
	public Post create(Post post) {
		Post result = postRepository.save(post);
		eventPublisher.publishEvent(new PostPublicationEvent(result, PostPublicationEvent.Type.CREATED));
		return result;
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		LocalDateTime now = LocalDateTime.now();

		ArchiveEntry entry = new ArchiveEntry();
		entry.setMonth(now.getMonthValue());
		entry.setPostCount(1);
		entry.setYear(now.getYear());
		archiveEntryRepository.save(entry);

		Post post = new Post();
		post.setAuthor("mcauthor");
		post.setCommentsAllowed(true);
		post.setContent("<p>This is a sample post that says some things and expands the information stored in the excerpt.</p>");
		post.setCreationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post.setExcerpt("<p>This is a sample post.</p>");
		post.setFeedContent("Post content processed for the feeds.");
		post.setLastModificationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post.setMetaDescription("This is the meta of the sample post");
		post.setPublicationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post.setPublished(true);
		post.setSlug("a-sample-post");
		post.setTitle("A sample post");
		post.setTags(Arrays.asList("a-tag", "another-tag"));
		postRepository.save(post);
		
		Post post2 = new Post();
		post2.setAuthor("admin");
		post2.setCommentsAllowed(false);
		post2.setContent("<p>Posted by the Administrator.</p>");
		post2.setCreationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post2.setExcerpt("<p>System information.</p>");
		post2.setFeedContent("System information. Posted by the Administrator.");
		post2.setLastModificationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post2.setMetaDescription("System information");
		post2.setPublicationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post2.setPublished(true);
		post2.setSlug("system-information");
		post2.setTitle("WARNING!");
		postRepository.save(post2);
		
		Post post3 = new Post();
		post3.setAuthor("mcauthor");
		post3.setCommentsAllowed(false);
		post3.setContent("<p>This post is tagged under A Tag.</p>");
		post3.setCreationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post3.setExcerpt("<p>Tagged post.</p>");
		post3.setFeedContent("This post is tagged under A Tag.");
		post3.setLastModificationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post3.setMetaDescription("Tagged post");
		post3.setPublicationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post3.setPublished(true);
		post3.setSlug("tagged-post");
		post3.setTitle("Tagged Post");
		post3.setTags(Arrays.asList("a-tag"));
		postRepository.save(post3);
	}

}
