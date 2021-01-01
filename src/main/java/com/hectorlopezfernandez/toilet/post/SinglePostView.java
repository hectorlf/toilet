package com.hectorlopezfernandez.toilet.post;

import java.util.Collection;

import com.hectorlopezfernandez.toilet.author.Author;
import com.hectorlopezfernandez.toilet.tag.Tag;

/**
 * View object that holds all the info for a single post
 * 
 * @author hector
 */
public class SinglePostView {

	private Post post;
	private Author author;
	private Collection<Tag> tags;

	public SinglePostView(Post post, Author author, Collection<Tag> tags) {
		this.author = author;
		this.post = post;
		this.tags = tags;
	}

	// getters & setters

	public Post getPost() {
		return post;
	}

	public Author getAuthor() {
		return author;
	}

	public Collection<Tag> getTags() {
		return tags;
	}

}
