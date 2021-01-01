package com.hectorlopezfernandez.toilet.post;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Archive Entries define year/month pairs that have at least one published post.
 * 
 * These entities are a performance artifact to avoid counting all of the
 * existing posts every time the /archive URL is requested. As such, these
 * should only exist when there's a published post in the given interval.
 * It wouldn't be catastrophic to have an entry with count 0, but that should be
 * avoided whenever possible.
 * 
 * Restrictions: the pair year/month is unique in the DB
 * 
 * @author hector
 */
@Document(collection="archiveEntries")	
public class ArchiveEntry {

	@Id
	private String id;
	private int year;
	private int month;
	private int count;

	// utility getters

	public LocalDateTime getAsDate() {
		return LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
	}

	// getters & setters

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		if (year < 1970) throw new IllegalArgumentException("Argument year must be greater or equal than 1970");
		this.year = year;
	}

	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		if (month < 1 || month > 12) throw new IllegalArgumentException("Argument month must be between 1 and 12");
		this.month = month;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
