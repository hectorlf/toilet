package com.hectorlopezfernandez.toilet.page;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Business logic around Pages
 */
@Service
public class PageService {

	private static final Logger logger = LoggerFactory.getLogger(PageService.class);

	private final PageRepository pageRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Inject
	public PageService(PageRepository pageRepository, ApplicationEventPublisher eventPublisher) {
		this.pageRepository = pageRepository;
		this.eventPublisher = eventPublisher;
	}

	/**
	 * Creates a Page
	 */
	public Page create(Page page) {
		Page result = pageRepository.save(page);
		eventPublisher.publishEvent(new PagePublicationEvent(result, PagePublicationEvent.Type.CREATED));
		return result;
	}

	/**
	 * Returns all the pages in the system, with some optional filtering. Never null.
	 */
	public List<Page> listPages(Map<String,String> filters) {
		logger.debug("Going into .listPages()");
		if (filters == null) throw new IllegalArgumentException("The filters parameter cannot be null");
		
		Optional<String> slug = Optional.ofNullable(filters.get("slug")); 
		List<Page> pages = pageRepository.findPagesFilteredBy(slug);
		return pages;
	}

	/**
	 * Returns the Page identified by the id argument.
	 */
	public Optional<Page> getPage(String id) {
		return pageRepository.findById(id);
	}

	/**
	 * Deletes a Page from the database.
	 */
	public void deletePage(String id) {
		logger.debug("Deleting Page with id: {}", id);
		if (id == null || id.isEmpty()) throw new IllegalArgumentException("Id argument can't be null");
		
		Optional<Page> existingPage = pageRepository.findById(id);
		if (existingPage.isEmpty()) return;

		pageRepository.deleteById(id);
		eventPublisher.publishEvent(new PageLifecycleEvent(existingPage.get(), PageLifecycleEvent.Type.DELETED));
	}

	// FIXME this helper function should only live until the admin console is built
	public void sample() {
		Instant now = Instant.now();

		Page page = new Page();
		page.setContent("<p>This is a sample about page. It's about... the about page.</p>");
		page.setCreationTime(now.toEpochMilli());
		page.setLastModificationTime(now.toEpochMilli());
		page.setMetaDescription("A sample about page");
		page.setPublicationTime(now.toEpochMilli());
		page.setPublished(true);
		page.setSlug("about");
		page.setTitle("About");
		pageRepository.save(page);
	}

}
