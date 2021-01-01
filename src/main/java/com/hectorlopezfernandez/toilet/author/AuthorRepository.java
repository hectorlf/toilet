package com.hectorlopezfernandez.toilet.author;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {

	Optional<Author> findBySlug(String slug);

	List<Author> findBySlugIn(Collection<String> slugs);

	List<Author.FeedProjection> findByIdIn(Collection<String> id);

}
