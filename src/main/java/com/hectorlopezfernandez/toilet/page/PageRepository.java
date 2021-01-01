package com.hectorlopezfernandez.toilet.page;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends MongoRepository<Page, String> {

	Page findBySlug(String slug);

	List<Page> findAllByPublishedIsTrueOrderByPublicationTimeDesc();

	// sitemap

	List<SitemapPageView> findAllByPublishedIsTrue();

}
