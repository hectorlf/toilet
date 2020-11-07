package com.hectorlopezfernandez.blog.author;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

	Optional<Author> findBySlug(String slug);

	List<Author.FeedProjection> findBySlugIn(Collection<String> slugs);

}
