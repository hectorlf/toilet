package com.hectorlopezfernandez.blog.security;

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
public class SecurityIndexConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(SecurityIndexConfigurer.class);

	private MongoTemplate mongoTemplate;

	@Inject
	public SecurityIndexConfigurer(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void ensureIndexes() {
		logger.info("Ensuring user indexes...");
		mongoTemplate.indexOps(Role.class).ensureIndex(new Index().on("name", Direction.ASC).unique());
		mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("username", Direction.ASC).unique());
	}

}