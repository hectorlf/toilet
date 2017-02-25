package com.hectorlopezfernandez.blog.page;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageServiceImpl implements PageService {

	@Autowired
	private PageRepository pageRepository;

	@Override
	public List<Page> listPagesForSitemap() {
		return pageRepository.findAllByPublishedIsTrue();
	}

}
