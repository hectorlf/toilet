package com.hectorlopezfernandez.blog.metadata;

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
public class MetadataIndexConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(MetadataIndexConfigurer.class);

	private MongoTemplate mongoTemplate;

	@Inject
	public MetadataIndexConfigurer(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ensureIndexes() {
		logger.info("Ensuring metadata indexes...");
		mongoTemplate.indexOps(Language.class).ensureIndex(new Index().on("tag", Direction.ASC).unique());
	}

}