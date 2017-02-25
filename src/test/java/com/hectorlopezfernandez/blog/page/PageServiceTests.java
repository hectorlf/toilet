package com.hectorlopezfernandez.blog.page;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
		Assert.assertNotNull(pages);
		Assert.assertTrue(pages.size() > 0);
	}

	@Before
	public void setup() {
		Page p = new Page();
		p.setTitle("Test");
		p.setPublished(true);
		pageRepository.save(p);
	}

	@After
	public void teardown() {
		pageRepository.deleteAll();
	}

}
