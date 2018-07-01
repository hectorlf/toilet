package com.hectorlopezfernandez.blog.page;

import java.util.List;

/**
 * Business logic around Pages
 */
public interface PageService {

	/**
	 * Returns a list of pages tailored for the sitemap
	 */
	List<Page> listPagesForSitemap();

}
