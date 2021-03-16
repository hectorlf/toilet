package com.hectorlopezfernandez.toilet.page;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

public class CustomPageRepositoryImpl implements CustomPageRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomPageRepositoryImpl.class);

	private final MongoOperations mongoOperations;

	@Inject
	public CustomPageRepositoryImpl(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	@Override
	public List<Page> findPagesFilteredBy(Optional<String> slug) {
		logger.debug("Going into .findPagesFilteredBy() with slug: {}", slug);
		if (slug == null) throw new IllegalArgumentException("Slug argument can't be null");
		Query query = new Query();
		if (slug.isPresent()) {
			query = query.addCriteria(where("slug").is(slug.get()));
		}
		return mongoOperations.find(query, Page.class);
	}

}
