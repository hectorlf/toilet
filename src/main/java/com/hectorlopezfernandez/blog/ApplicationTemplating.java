package com.hectorlopezfernandez.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hectorlopezfernandez.pebble.resourcer.ResourcerExtension;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.spring.extension.SpringExtension;

@Configuration
public class ApplicationTemplating {

	// workaround for a pebble-spring-boot-starter bug
	@Bean
	public Extension springExtension() {
		return new SpringExtension();
	}

	/*
	 * @Bean public Extension springSecurityExtension() { return new
	 * SpringSecurityExtension(); }
	 * 
	 * @Bean public Extension jodaExtension() { return new JodaExtension(); }
	 */

	@Bean
	@Autowired
	public Extension resourcerExtension(Environment environment) {
		return new ResourcerExtension(environment);
	}

}