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
public class ArchiveEntryIndexConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(ArchiveEntryIndexConfigurer.class);

	private final MongoTemplate mongoTemplate;

	@Inject
	public ArchiveEntryIndexConfigurer(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ensureIndexes() {
		logger.info("Ensuring archive entry indexes...");
		mongoTemplate.indexOps(ArchiveEntry.class).ensureIndex(new Index().on("year", Direction.DESC).on("month", Direction.DESC).unique());
	}

}