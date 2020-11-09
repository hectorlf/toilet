package com.hectorlopezfernandez.blog;

import static com.hectorlopezfernandez.blog.security.WellKnownRoles.ADMIN;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hectorlopezfernandez.blog.security.SecurityService;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

	private static final String[] MANAGEMENT_ENDPOINTS = {"/management/dump","/management/health","/management/metrics","/management/trace","/management/loggers"};

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// general properties
		http.csrf().disable();
		/*
		 * if (securityProperties.getUser().isRequireSsl())
		 * http.requiresChannel().anyRequest().requiresSecure(); if
		 * (!securityProperties.isEnableCsrf()) http.csrf().disable(); if
		 * (!securityProperties.getHeaders().isFrame())
		 * http.headers().frameOptions().disable(); if
		 * (!securityProperties.getHeaders().isContentType())
		 * http.headers().contentTypeOptions().disable(); if
		 * (!securityProperties.getHeaders().isXss())
		 * http.headers().xssProtection().disable(); if
		 * (securityProperties.getHeaders().getHsts() != Headers.HSTS.NONE)
		 * http.headers().httpStrictTransportSecurity().includeSubDomains(
		 * securityProperties.getHeaders().getHsts() == Headers.HSTS.ALL);
		 * http.sessionManagement().sessionCreationPolicy(securityProperties.getSessions
		 * ());
		 */		// login config
		http.formLogin().loginPage("/login.page").loginProcessingUrl("/login").defaultSuccessUrl("/index.page").failureUrl("/login.page?error");
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/index.page");
		// management access rules
		http.requiresChannel().antMatchers(MANAGEMENT_ENDPOINTS).requiresSecure();
		http.authorizeRequests().antMatchers(MANAGEMENT_ENDPOINTS).hasAuthority(ADMIN.name());
		// app access rules
		http.requiresChannel().antMatchers("/login","/logout","/login.page","/admin/*.page","/admin/api/*").requiresSecure();
		http.authorizeRequests().antMatchers("/admin/*.page","/admin/api/*").hasAuthority(ADMIN.name());
		// default access rules
		http.authorizeRequests().antMatchers("/**").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityService).passwordEncoder(new BCryptPasswordEncoder());
	}

}