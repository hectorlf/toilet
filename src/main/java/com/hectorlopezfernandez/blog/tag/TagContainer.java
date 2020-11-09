package com.hectorlopezfernandez.blog.tag;

import java.util.List;

public class TagContainer {

	private List<Tag> tags;
	private int total;
	
	public TagContainer(List<Tag> tags, int total) {
		if (tags == null) throw new IllegalArgumentException("Parameter tags cannot be null");
		this.tags = tags;
		this.total = total;
	}	

	// getters & setters

	public List<Tag> getTags() {
		return tags;
	}

	public int getTotal() {
		return total;
	}

}
