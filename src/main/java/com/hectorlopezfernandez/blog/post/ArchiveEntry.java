package com.hectorlopezfernandez.blog.post;

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
		this.month = month;
	}

	// projections

	/**
	 * Projection interface to retrieve only years
	 */
	public interface YearlyEntry {

		int getYear();

	}

}
