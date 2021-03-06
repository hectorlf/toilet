package com.hectorlopezfernandez.toilet.tag;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.count;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Query;

import com.hectorlopezfernandez.toilet.post.Post;

public class CustomTagRepositoryImpl implements CustomTagRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomTagRepositoryImpl.class);

	private final MongoTemplate mongoTemplate;

	@Inject
	public CustomTagRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Tag> findTagsFilteredBy(Optional<String> slug) {
		logger.debug("Going into .findTagsFilteredBy() with slug: {}", slug);
		if (slug == null) throw new IllegalArgumentException("Slug argument can't be null");
		Query query = new Query();
		if (slug.isPresent()) {
			query = query.addCriteria(where("slug").is(slug.get()));
		}
		return mongoTemplate.find(query, Tag.class);
	}

	@Override
	public void updateTagCount(String id) {
		logger.debug("Going into .updateTagCount() with id: {}", id);
		if (id == null || id.isBlank()) throw new IllegalArgumentException("Id argument can't be null or blank");
		Aggregation tagCountQuery = Aggregation.newAggregation(
				match(where("tags").is(id).and("published").is(true)),
				group("id"),
				count().as("count"));
		CountResults results = mongoTemplate.aggregate(tagCountQuery, Post.class, CountResults.class).getUniqueMappedResult();
		int postCount = results == null ? 0 : results.count;
		mongoTemplate.updateFirst(query(where("id").is(id)), update("count", postCount), Tag.class);
	}

	private class CountResults {
		public int count;
	}

}
