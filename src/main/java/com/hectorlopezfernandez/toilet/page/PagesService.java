package com.hectorlopezfernandez.toilet.page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	 * Returns the Page identified by its slug
	 */
	public Optional<Page> getPageBySlug(String slug) {
		return Optional.ofNullable(pageRepository.findBySlug(slug));
	}

	// sitemap

	/**
	 * Returns a list of pages tailored for the sitemap
	 */
	public List<SitemapPageView> listPagesForSitemap() {
		List<SitemapPageView> pages = pageRepository.findAllByPublishedIsTrue().stream()
				.map(element -> new SitemapPageView(element.getSlug(), element.getLastModificationTime()))
				.collect(Collectors.toList());
		return pages;
	}

}
