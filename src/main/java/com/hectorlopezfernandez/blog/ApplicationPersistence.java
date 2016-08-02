package com.hectorlopezfernandez.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

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

}