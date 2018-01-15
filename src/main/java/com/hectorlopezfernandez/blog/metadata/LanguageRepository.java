package com.hectorlopezfernandez.blog.metadata;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository-style data access object for the Language class.
 */
@Repository
interface LanguageRepository extends MongoRepository<Language, String> {

	/**
	 * Searches the DB for a specific language tag.
	 * 
	 * @param languageTag  the language tag that identifies the object
	 * @return  the {@link Language} object corresponding to the tag, if any
	 */
	Language findByTag(String languageTag);

	/**
	 * Obtains the collection of different language tags contained in the DB.
	 * 
	 * @return  the list of all language tags in the DB
	 */
	@Query(value="{}", fields="{tag : 1, _id : 0}")
	List<Language.TagOnly> findAllTags();

}