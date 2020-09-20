package com.hectorlopezfernandez.blog.post;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	// admin section

	Optional<Post> findOneById(String id);

	// index section

	Page<Post> findByPublishedIsTrueOrderByPublicationTimeDesc(Pageable pageable);

	// archive section

	@Query(value = "{ 'publicationTime': { $gte: ?0, $lt: ?1 }, 'published': true }", sort = "{ 'publicationTime': -1 }")
	List<Post> findPublishedBetween(long startTime, long endTime);

	Post findBySlug(String slug);

	// sitemap

	List<Post> findAllByPublishedIsTrue();

	// feeds

	List<Post> findByCreationTimeLessThanEqual(long date);

}
