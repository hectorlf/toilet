package com.hectorlopezfernandez.blog.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	Post findBySlug(String slug);

	List<Post> findByCreationTimeLessThanEqual(long date);

	Page<Post> findByPublishedIsTrueOrderByPublicationTimeDesc(Pageable pageable);

	List<Post> findAllByPublishedIsTrue();

	Optional<Post> findOneById(String id);

}
