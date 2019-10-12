package com.hectorlopezfernandez.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

@Configuration
public class TestApplicationPersistence {

	private static final String DB_NAME = "blog";

	@Bean
	public MongoTemplate mongoTemplate(MongoClient mongoClient) {
		return new MongoTemplate(mongoDbFactory(mongoClient));
	}

	@Bean
	public MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
		return new SimpleMongoDbFactory(mongoClient, DB_NAME);
	}
	
	@Bean(destroyMethod="shutdown")
	public MongoServer mongoServer() {
		MongoServer mongoServer = new MongoServer(new MemoryBackend());
		mongoServer.bind();
		return mongoServer;
	}

	@Bean(destroyMethod="close")
	public MongoClient mongoClient(MongoServer mongoServer) {
		return new MongoClient(new ServerAddress(mongoServer.getLocalAddress()));
	}

}