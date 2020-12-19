package com.hectorlopezfernandez.blog.post;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;

public class CustomPostRepositoryImpl implements CustomPostRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomPostRepositoryImpl.class);

	private final MongoTemplate mongoTemplate;

	@Inject
	public CustomPostRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void removeAllTagsWithId(String id) {
		logger.debug("Going into .removeAllTagsWithId() with id: {}", id);
		mongoTemplate.updateMulti(query(where("tags").is(id)), new Update().pull("tags", id), Post.class);
	}

}
