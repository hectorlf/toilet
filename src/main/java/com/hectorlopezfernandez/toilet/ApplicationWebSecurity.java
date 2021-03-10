package com.hectorlopezfernandez.toilet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hectorlopezfernandez.toilet.security.SecurityService;
import com.hectorlopezfernandez.toilet.security.WellKnownRoles;

@Configuration
@Order(100)
public class ApplicationWebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private SecurityService securityService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.antMatcher("/**")
			.requiresChannel().anyRequest().requiresSecure()
			.and()
			.authorizeRequests().antMatchers("/admin/*.page").hasAuthority(WellKnownRoles.ADMIN.name())
			.and()
			.authorizeRequests().anyRequest().permitAll()
			.and()
			.formLogin().loginPage("/login.page").loginProcessingUrl("/login").defaultSuccessUrl("/index.page").failureUrl("/login.page?error")
			.and()
			.logout().logoutUrl("/logout").logoutSuccessUrl("/index.page");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(securityService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
