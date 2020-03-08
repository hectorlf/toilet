package com.hectorlopezfernandez.blog.author;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

	private AuthorRepository authorRepository;

	@Inject
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

}