package com.hectorlopezfernandez.blog.metadata;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository-style data access object for the Language class.
 */
@Repository
public interface PreferencesRepository extends MongoRepository<Preferences, String> {

	/**
	 * Returns the global preferences.
	 * 
	 * @return  the preferences configured for the system
	 */
	@Query("{ \"_id\" : \"" + Preferences.ID + "\" }")
	Preferences get();

}
