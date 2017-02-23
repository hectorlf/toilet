package com.hectorlopezfernandez.blog.page;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends MongoRepository<Page, String> {

	List<Page> findByTitle(String title);

}
