package com.hectorlopezfernandez.blog.page;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class PageServiceTests extends BaseTest {

	@Autowired
	private PageService pageService;
	
	@Autowired
	private PageRepository pageRepository;

	@Test
	public void testShouldReturnAtLeastOnePage() {
		List<Page> pages = pageService.listPagesForSitemap();
		Assertions.assertNotNull(pages);
		Assertions.assertTrue(pages.size() > 0);
	}

	@BeforeEach
	public void setup() {
		Page p = new Page();
		p.setTitle("Test");
		p.setPublished(true);
		pageRepository.save(p);
	}

	@AfterEach
	public void teardown() {
		pageRepository.deleteAll();
	}

}
