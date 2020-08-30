package com.hectorlopezfernandez.blog.author;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class AuthorService {

	private final AuthorRepository authorRepository;

	@Inject
	public AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	/**
	 * Returns the Author identified by its slug
	 */
	public Optional<Author> getAuthorBySlug(String slug) {
		return authorRepository.findBySlug(slug);
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		Author author = new Author();
		author.setAbout("An author...");
		author.setDisplayName("Content McAuthor");
		author.setRelatedUrl("https://disney.com");
		author.setSlug("mcauthor");
		authorRepository.save(author);
	}

}
