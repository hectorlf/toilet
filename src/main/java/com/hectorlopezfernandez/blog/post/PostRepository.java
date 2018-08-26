package com.hectorlopezfernandez.blog.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	List<Post> findByTitle(String title);

	List<Post> findByCreationDateLessThanEqual(long date);

	Page<Post> findByPublishedIsTrueOrderByPublicationDateDesc(Pageable pageable);

	List<Post> findAllByPublishedIsTrue();

	Post findOneById(String id);

}
