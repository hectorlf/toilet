package com.hectorlopezfernandez.blog.page;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hectorlopezfernandez.blog.BaseTest;

public class PageRepositoryTests extends BaseTest {
	
	@Autowired
	private PageRepository pageRepository;

	@Test
	public void testPages() {
		Assert.assertTrue(pageRepository.findAll().size() == 2);
		List<Page> pageList = pageRepository.findByTitle("Title2");
		Assert.assertNotNull(pageList);
		Assert.assertTrue(pageList.size() == 1);
		Assert.assertEquals("Title2", pageList.get(0).getTitle());
		pageList = pageRepository.findAllByPublishedIsTrue();
		Assert.assertNotNull(pageList);
		Assert.assertTrue(pageList.size() == 1);
		pageList = pageRepository.findByTitle("nonexistent");
		Assert.assertNotNull(pageList);
		Assert.assertTrue(pageList.size() == 0);
	}

	@Before
	public void setup() {
		Page p = new Page();
		p.setContent("Content1");
		p.setPublicationDate(System.currentTimeMillis());
		p.setTitle("Title1");
		p.setPublished(true);
		pageRepository.save(p);
		p = new Page();
		p.setContent("Content2");
		p.setPublicationDate(Long.valueOf(0));
		p.setTitle("Title2");
		pageRepository.save(p);
	}

	@After
	public void teardown() {
		pageRepository.deleteAll();
	}

}
