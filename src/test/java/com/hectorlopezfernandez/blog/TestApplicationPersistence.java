package com.hectorlopezfernandez.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;

@Configuration
public class TestApplicationPersistence {

	private static final String DB_NAME = "blog";

	@Bean
	public MongoClient testMongoDb() {
		Fongo fongo = new Fongo(DB_NAME);
		return fongo.getMongo();
	}

	@Bean
	public MongoDbFactory mongoDbFactory() {
		return new SimpleMongoDbFactory(testMongoDb(), DB_NAME);
	}

}