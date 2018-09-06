package com.hectorlopezfernandez.blog.post;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entries define month/year pairs that have at least one published post 
 * 
 * @author hector
 */
@Document(collection="archiveEntries")
@CompoundIndexes({
	@CompoundIndex(def="{'year': -1, 'month': 1}", unique=true)
})
public class ArchiveEntry {

	@Id
	private String id;
	
	private int year;

	private int month;
	
	private int postCount;

	// utility getters

	public DateTime getAsDate() {
		return new DateTime(year, month + 1, 1, 0, 0);
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
		this.year = year;
	}

	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		if (month < 0 || month > 11) throw new IllegalArgumentException("Argument month must be between 0 and 11");
		this.month = month;
	}

	public int getPostCount() {
		return postCount;
	}
	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

}
