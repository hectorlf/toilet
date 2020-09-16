package com.hectorlopezfernandez.blog.sitemap;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hectorlopezfernandez.blog.page.Page;
import com.hectorlopezfernandez.blog.page.PagesService;
import com.hectorlopezfernandez.blog.post.ArchiveService;
import com.hectorlopezfernandez.blog.post.Post;

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

	@RequestMapping(value="/sitemap.xml")
	public String sitemap(ModelMap model, HttpServletResponse response) {
		logger.debug("Going into SitemapController.sitemap()");
		List<Post> posts = archiveService.listPostsForSitemap();
		model.addAttribute("posts", posts);
		List<Page> pages = pagesService.listPagesForSitemap();
		model.addAttribute("pages", pages);
		// special content type
		response.setContentType("text/xml");
		return "web/pages/sitemap";
	}

}
