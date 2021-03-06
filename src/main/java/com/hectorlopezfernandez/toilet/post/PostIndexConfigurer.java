package com.hectorlopezfernandez.toilet.post;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

/**
 * Registers an EventListener that initializes the mongodb indexes
 */
@Configuration
public class PostIndexConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(PostIndexConfigurer.class);

	private final MongoTemplate mongoTemplate;

	@Inject
	public PostIndexConfigurer(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ensureIndexes() {
		logger.info("Ensuring post indexes...");
		mongoTemplate.indexOps(Post.class).ensureIndex(new Index().on("slug", Direction.ASC).unique());
		mongoTemplate.indexOps(Post.class)
				.ensureIndex(new Index().on("published", Direction.ASC).on("publicationTime", Direction.DESC));
		mongoTemplate.indexOps(Post.class)
				.ensureIndex(new Index().on("published", Direction.ASC).on("slug", Direction.ASC)
						.on("publicationTime", Direction.DESC).on("lastModificationTime", Direction.DESC));
		mongoTemplate.indexOps(Post.class).ensureIndex(new Index().on("tags", Direction.ASC));
	}

}