package com.hectorlopezfernandez.blog.post;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class PostService {

	private final PostRepository postRepository;
	private final ArchiveEntryRepository archiveEntryRepository;

	@Inject
	public PostService(PostRepository postRepository, ArchiveEntryRepository archiveEntryRepository) {
		this.postRepository = postRepository;
		this.archiveEntryRepository = archiveEntryRepository;
	}

	/**
	 * Creates a Post
	 */
	public Post create(Post post) {
		return postRepository.save(post);
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
		post.setAuthor("shakespeare");
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
		postRepository.save(post);
	}

}
