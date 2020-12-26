package com.hectorlopezfernandez.blog;

import java.net.InetSocketAddress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

/**
 * NOTE: Basic mongodb configuration takes place in application.properties
 * 
 * @author hector
 */
@Configuration
public class ApplicationPersistence {

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Configuration
	@Profile("default")
	public static class InMemoryPersistence {

		@Bean
		public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDatabaseFactory) {
			return new MongoTemplate(mongoDatabaseFactory);
		}

		@Bean
		public MongoDatabaseFactory mongoDbFactory(MongoServer mongoServer) {
			InetSocketAddress serverAddress = mongoServer.getLocalAddress();
			return new SimpleMongoClientDatabaseFactory("mongodb://" + serverAddress.getHostName() + ":" + serverAddress.getPort() + "/blog");
		}
		
		@Bean(destroyMethod="shutdown")
		public MongoServer mongoServer() {
			MongoServer mongoServer = new MongoServer(new MemoryBackend());
			mongoServer.bind();
			return mongoServer;
		}

	}

}