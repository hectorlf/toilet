package com.hectorlopezfernandez.blog.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Business logic around Pages
 */
@Service
public class PageService {

	@Autowired
	private PageRepository pageRepository;

	/**
	 * Returns a list of pages tailored for the sitemap
	 */
	public List<Page> listPagesForSitemap() {
		return pageRepository.findAllByPublishedIsTrue();
	}

}
