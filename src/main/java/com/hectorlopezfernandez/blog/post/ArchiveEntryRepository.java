package com.hectorlopezfernandez.blog.post;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hectorlopezfernandez.blog.post.ArchiveEntry.YearlyEntry;

@Repository
public interface ArchiveEntryRepository extends MongoRepository<ArchiveEntry, String> {

	List<YearlyEntry> findAllDistinctBy();

}
