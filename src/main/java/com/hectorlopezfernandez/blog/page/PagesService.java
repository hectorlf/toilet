package com.hectorlopezfernandez.blog.page;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Implements the logic to handle /pages
 */
@Service
public class PagesService {

	private final PageRepository pageRepository;

	@Inject
	public PagesService(PageRepository pageRepository) {
		this.pageRepository = pageRepository;
	}

	/**
	 * Returns a list of pages tailored for the sitemap
	 */
	public List<Page> listPagesForSitemap() {
		return pageRepository.findAllByPublishedIsTrue();
	}

	/**
	 * Returns the Page identified by its slug
	 */
	public Optional<Page> getPageBySlug(String slug) {
		return Optional.ofNullable(pageRepository.findBySlug(slug));
	}

}
