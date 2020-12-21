package com.hectorlopezfernandez.blog.post;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.hectorlopezfernandez.blog.author.Author;

/**
 * View object specific to the feeds
 * 
 * @author hector
 */
public class FeedPostsView {

	private List<FeedPostView> posts;
	private long lastUpdatedOnTime;

	public FeedPostsView(List<Post.FeedProjection> postProjections, List<Author.FeedProjection> authorProjections) {
		this.posts = new LinkedList<FeedPostView>();
		if (postProjections != null && !postProjections.isEmpty()) {
			if (authorProjections == null || authorProjections.isEmpty()) throw new IllegalArgumentException("The parameter authorProjections can't be empty because postProjections is not empty");
			Map<String,String> authorNames = authorProjections.stream()
				.collect(Collectors.toMap(Author.FeedProjection::getId, Author.FeedProjection::getDisplayName, (value1, value2) -> value1));
			postProjections.stream().forEach(post -> {
				if (!authorNames.containsKey(post.getAuthor())) throw new IllegalArgumentException("The parameter authorProjections doesn't contain an entry for the author id: " + post.getAuthor());
				this.posts.add(new FeedPostView(post.getPublicationTime(), post.getLastModificationTime(), 
					post.getTitle(), post.getSlug(), post.getFeedContent(), authorNames.get(post.getAuthor())));
			});
			this.lastUpdatedOnTime = postProjections.stream().map(Post.FeedProjection::getLastModificationTime).max(Long::compare).orElse(Long.valueOf(0));
		}
	}

	// getters & setters

	public List<FeedPostView> getPosts() {
		return posts;
	}

	public LocalDateTime getLastUpdatedOnDate() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(lastUpdatedOnTime), ZoneId.systemDefault());
	}

	// individual post holder

	public class FeedPostView {
		private long publicationTime;
		private long lastModificationTime;
		private String title;
		private String slug;
		private String feedContent;
		private String authorName;
		
		private FeedPostView(long publicationTime, long lastModificationTime, String title, String slug, 
				String feedContent, String authorName) {
			this.publicationTime = publicationTime;
			this.lastModificationTime = lastModificationTime;
			this.title = title;
			this.slug = slug;
			this.feedContent = feedContent;
			this.authorName = authorName;
		}

		public LocalDateTime getPublicationTimeAsDate() {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(publicationTime), ZoneId.systemDefault());
		}

		public LocalDateTime getLastModificationTimeAsDate() {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModificationTime), ZoneId.systemDefault());
		}

		public String getTitle() {
			return title;
		}

		public String getSlug() {
			return slug;
		}

		public String getFeedContent() {
			return feedContent;
		}

		public String getAuthorName() {
			return authorName;
		}
	}

}
