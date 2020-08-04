package com.hectorlopezfernandez.blog.tag;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

	Tag findBySlug(String slug);

	Optional<Tag> findOneById(String id);

}