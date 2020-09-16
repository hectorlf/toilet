package com.hectorlopezfernandez.blog.page;

import org.springframework.context.ApplicationEvent;

/**
 * Signals that the lifecycle of a Page has changed state. This will be listened by
 * some components like the search index or caches.
 * 
 * @author hector
 */
public class PagePublicationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 5641735021969808994L;

	private final Type type;

	public PagePublicationEvent(Page page, Type type) {
		super(page);
		this.type = type;
	}

	@Override
	public Page getSource() {
		return (Page) super.getSource();
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
