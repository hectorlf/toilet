package com.hectorlopezfernandez.blog.metadata;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends MongoRepository<Language, String> {

	Language findByPrimaryIsTrue();

}