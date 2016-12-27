package com.hectorlopezfernandez.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hectorlopezfernandez.pebble.springsecurity.SpringSecurityExtension;
import com.hectorlopezfernandez.pebblejodatime.JodaExtension;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.spring4.extension.SpringExtension;

@Configuration
public class ApplicationTemplating {

	// workaround for a pebble-spring-boot-starter bug
	@Bean
	public Extension springExtension() {
		return new SpringExtension();
	}

	@Bean
	public Extension springSecurityExtension() {
		return new SpringSecurityExtension();
	}

	@Bean
	public Extension jodaExtension() {
		return new JodaExtension();
	}

}