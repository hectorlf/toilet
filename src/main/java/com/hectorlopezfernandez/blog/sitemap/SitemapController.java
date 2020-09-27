package com.hectorlopezfernandez.blog.sitemap;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hectorlopezfernandez.blog.page.PagesService;
import com.hectorlopezfernandez.blog.page.SitemapPageView;
import com.hectorlopezfernandez.blog.post.ArchiveService;
import com.hectorlopezfernandez.blog.post.SitemapPostView;

@Controller
public class SitemapController {

	private static final Logger logger = LoggerFactory.getLogger(SitemapController.class);

	private final ArchiveService archiveService;
	private final PagesService pagesService;

	@Inject
	public SitemapController(ArchiveService archiveService, PagesService pagesService) {
		this.archiveService = archiveService;
		this.pagesService = pagesService;
	}

	@RequestMapping(value="/sitemap.xml", method=RequestMethod.GET, produces=MediaType.TEXT_XML_VALUE)
	public String sitemap(ModelMap model, HttpServletResponse response) {
		logger.debug("Going into .sitemap()");
		List<SitemapPostView> posts = archiveService.listPostsForSitemap();
		model.addAttribute("posts", posts);
		List<SitemapPageView> pages = pagesService.listPagesForSitemap();
		model.addAttribute("pages", pages);
		// required to override Pebble's default content type
		response.setContentType("text/xml");
		return "web/pages/sitemap";
	}

}
