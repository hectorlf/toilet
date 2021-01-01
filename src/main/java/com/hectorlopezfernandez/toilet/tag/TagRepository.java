package com.hectorlopezfernandez.toilet.tag;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, String>, CustomTagRepository {

	Optional<Tag> findBySlug(String slug);

	/**
	 * Returns whether a Tag with the given slug exists.
	 */
	boolean existsBySlug(String slug);

}
