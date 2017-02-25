package com.hectorlopezfernandez.blog.page;

import java.util.List;

public interface PageService {

	/**
	 * Returns a list of pages tailored for the sitemap
	 */
	List<Page> listPagesForSitemap();

}
