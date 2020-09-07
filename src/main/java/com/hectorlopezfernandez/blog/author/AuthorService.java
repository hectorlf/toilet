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

	// initialization

	public void initialize() {
		Author author = new Author();
		author.setAbout("I am the law");
		author.setDisplayName("Administrator");
		author.setSlug("admin");
		authorRepository.save(author);
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		Author adminAuthor = new Author();
		adminAuthor.setAbout("I am the law");
		adminAuthor.setDisplayName("Administrator");
		adminAuthor.setSlug("admin");
		authorRepository.save(adminAuthor);

		Author author = new Author();
		author.setAbout("An author...");
		author.setDisplayName("Content McAuthor");
		author.setRelatedUrl("https://disney.com");
		author.setSlug("mcauthor");
		authorRepository.save(author);
	}

}
