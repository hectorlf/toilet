package com.hectorlopezfernandez.blog.page;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

/**
 * Business logic around Pages
 */
@Service
public class PageService {

	private final PageRepository pageRepository;

	@Inject
	public PageService(PageRepository pageRepository) {
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

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		Instant now = Instant.now();

		Page page = new Page();
		page.setContent("<p>This is a sample about page. It's about... the about page.</p>");
		page.setLastModificationDate(now.toEpochMilli());
		page.setMetaDescription("A sample about page");
		page.setPublicationDate(now.toEpochMilli());
		page.setPublished(true);
		page.setSlug("about");
		page.setTitle("About");
		pageRepository.save(page);
	}

}
