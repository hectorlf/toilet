package com.hectorlopezfernandez.blog.post;

import org.springframework.context.ApplicationEvent;

/**
 * Signals that the lifecycle of a Post has changed state. This will be listened by
 * some components like the search index, tag counts or caches.
 * 
 * @author hector
 */
public class PostPublicationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6259906107914696684L;
	
	private final Type type;

	public PostPublicationEvent(Post post, Type type) {
		super(post);
		this.type = type;
	}

	@Override
	public Post getSource() {
		return (Post) super.getSource();
	}

	public Type getType() {
		return type;
	}

	public static enum Type {
		CREATED,
		UPDATED,
		PUBLISHED,
		UNPUBLISHED,
		DELETED
	}

}
