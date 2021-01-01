package com.hectorlopezfernandez.toilet.post;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.count;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

public class CustomArchiveEntryRepositoryImpl implements CustomArchiveEntryRepository {

	private static final Logger logger = LoggerFactory.getLogger(CustomArchiveEntryRepositoryImpl.class);

	private final MongoTemplate mongoTemplate;

	@Inject
	public CustomArchiveEntryRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void updateEntryByTime(long time) {
		logger.debug("Going into .updateEntryByTime() with time: {}", time);
		LocalDateTime pubicationTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
		LocalDateTime startOfMonth = pubicationTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime startOfNextMonth = startOfMonth.plusMonths(1);
		int postCountAtDate = countPostsBetween(startOfMonth.toInstant(ZoneOffset.UTC).toEpochMilli(),
				startOfNextMonth.toInstant(ZoneOffset.UTC).toEpochMilli());
		if (postCountAtDate == 0) {
			// no ArchiveEntry should exist if there's no post visible in its interval
			mongoTemplate.remove(query(where("year").is(pubicationTime.getYear()).and("month").is(pubicationTime.getMonthValue())), 
					ArchiveEntry.class);
		} else {
			// it's possible that no prior ArchiveEntry exists, so upsert
			mongoTemplate.upsert(query(where("year").is(pubicationTime.getYear()).and("month").is(pubicationTime.getMonthValue())), 
					update("count", postCountAtDate), ArchiveEntry.class);
		}
	}

	private int countPostsBetween(long startMillis, long endMillis) {
		logger.debug("Going into .countPostsBetween() with start: {}, and end: {}", startMillis, endMillis);
		Aggregation postCountQuery = Aggregation.newAggregation(
				match(where("publicationTime").gte(startMillis).lt(endMillis).and("published").is(true)),
				group("id"),
				count().as("count"));
		CountResults results = mongoTemplate.aggregate(postCountQuery, Post.class, CountResults.class).getUniqueMappedResult();
		return results == null ? 0 : results.count;
	}

	private class CountResults {
		public int count;
	}

}
