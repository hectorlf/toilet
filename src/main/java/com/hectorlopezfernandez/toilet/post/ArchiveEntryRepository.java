package com.hectorlopezfernandez.toilet.post;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveEntryRepository extends MongoRepository<ArchiveEntry, String>, CustomArchiveEntryRepository {

}
