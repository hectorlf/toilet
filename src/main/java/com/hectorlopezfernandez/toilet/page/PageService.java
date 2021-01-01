package com.hectorlopezfernandez.toilet.page;

import java.time.Instant;

import javax.inject.Inject;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Business logic around Pages
 */
@Service
public class PageService {

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
