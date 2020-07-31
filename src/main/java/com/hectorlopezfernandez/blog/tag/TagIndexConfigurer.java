package com.hectorlopezfernandez.blog.tag;

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
public class TagIndexConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(TagIndexConfigurer.class);

	private MongoTemplate mongoTemplate;

	@Inject
	public TagIndexConfigurer(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ensureIndexes() {
		logger.info("Ensuring tag indexes...");
		mongoTemplate.indexOps(Tag.class).ensureIndex(new Index().on("slug", Direction.ASC).unique());
	}

}
