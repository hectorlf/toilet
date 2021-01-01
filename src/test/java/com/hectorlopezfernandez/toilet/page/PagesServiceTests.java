package com.hectorlopezfernandez.toilet.page;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.toilet.BaseTest;

public class PagesServiceTests extends BaseTest {

	@Autowired
	private PagesService pagesService;
	
	@Autowired
	private PageRepository pageRepository;

	@Test
	public void testShouldReturnAtLeastOnePage() {
		List<SitemapPageView> pages = pagesService.listPagesForSitemap();
		Assertions.assertNotNull(pages);
		Assertions.assertTrue(pages.size() == 1);
	}

	@BeforeEach
	public void setup() {
		Page p = new Page();
		p.setTitle("Test");
		p.setSlug("test1");
		p.setPublished(true);
		pageRepository.save(p);
		Page p2 = new Page();
		p2.setTitle("Test2");
		p2.setSlug("test2");
		p2.setPublished(false);
		pageRepository.save(p2);
	}

	@AfterEach
	public void teardown() {
		pageRepository.deleteAll();
	}

}
