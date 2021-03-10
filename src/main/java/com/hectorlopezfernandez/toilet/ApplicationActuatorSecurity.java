package com.hectorlopezfernandez.toilet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import com.hectorlopezfernandez.toilet.security.SecurityService;
import com.hectorlopezfernandez.toilet.security.WellKnownRoles;

@Configuration
@Order(98)
public class ApplicationActuatorSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityService securityService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.antMatcher("/actuator/**")
			.requiresChannel().anyRequest().requiresSecure()
			.and()
			.authorizeRequests().antMatchers("/actuator/health").permitAll()
			.and()
			.authorizeRequests().anyRequest().hasAuthority(WellKnownRoles.ADMIN.name())
			.and()
			.exceptionHandling()
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityService).passwordEncoder(new BCryptPasswordEncoder());
	}

}