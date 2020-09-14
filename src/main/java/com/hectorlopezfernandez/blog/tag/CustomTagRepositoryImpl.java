package com.hectorlopezfernandez.blog.tag;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.count;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import com.hectorlopezfernandez.blog.post.Post;

public class CustomTagRepositoryImpl implements CustomTagRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomTagRepositoryImpl.class);

	private final MongoTemplate mongoTemplate;

	@Inject
	public CustomTagRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void updateCountBySlug(String slug) {
		logger.debug("Going into .updateCountBySlug() with slug {}", slug);
		if (slug == null || slug.isBlank()) throw new IllegalArgumentException("Slug argument can't be null or blank");
		Aggregation tagCountQuery = Aggregation.newAggregation(
				match(where("tags").is(slug).and("published").is(true)),
				group("id"),
				count().as("count"));
		CountResults results = mongoTemplate.aggregate(tagCountQuery, Post.class, CountResults.class).getUniqueMappedResult();
		int postCount = results == null ? 0 : results.count;
		mongoTemplate.updateFirst(query(where("slug").is(slug)), update("count", postCount), Tag.class);
	}

	private class CountResults {
		public int count;
	}

}
