package com.hectorlopezfernandez.toilet.post;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.hectorlopezfernandez.toilet.tag.TagLifecycleEvent;

@Service
public class PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostService.class);

	private final PostRepository postRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Inject
	public PostService(PostRepository postRepository, ApplicationEventPublisher eventPublisher) {
		this.postRepository = postRepository;
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

	@EventListener
	public void removeTagFromPosts(TagLifecycleEvent event) {
		TagLifecycleEvent.Type eventType = event.getType();
		String tagId = event.getSource().getId();
		logger.debug("Event of type {} received for Tag with id: {}", eventType, tagId);
		if (eventType == TagLifecycleEvent.Type.DELETED) {
			// FIXME this needs to send an event so index and caches are updated
			// Probably it makes more sense to update post by post since we need to reindex them anyway
			postRepository.removeAllTagsWithId(tagId);
		}
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		LocalDateTime now = LocalDateTime.now();

		Post post = new Post();
		post.setAuthor("2");
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
		post.setTags(Arrays.asList("1", "2"));
		postRepository.save(post);

		Post post2 = new Post();
		post2.setAuthor("1");
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
		post3.setAuthor("2");
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
		post3.setTags(Arrays.asList("1"));
		postRepository.save(post3);

		Post post4 = new Post();
		post4.setAuthor("2");
		post4.setCommentsAllowed(false);
		post4.setContent("<p>And here is a bigger code snippet:</p><pre><code class=\"language-java\">"
				+ "@Document(collection=\"tags\")\n"
				+ "public class Tag {\n"
				+ "  @Id\n"
				+ "  private String id;\n"
				+ "  private String slug;\n"
				+ "  private String name;\n"
				+ "  private int count;\n"
				+ "}</code></pre>");
		post4.setCreationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post4.setExcerpt("<p>Here's a small code snippet:</p><pre><code class=\"language-java\">public class PostService { }</code></pre>");
		post4.setFeedContent("This post contains a code snippet.");
		post4.setLastModificationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post4.setMetaDescription("Post with a code snippet");
		post4.setPublicationTime(now.toInstant(ZoneOffset.UTC).toEpochMilli());
		post4.setPublished(true);
		post4.setSlug("code-snippet");
		post4.setTitle("Code snippet");
		post4.setTags(Arrays.asList("1"));
		postRepository.save(post4);
	}

}
