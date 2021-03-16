package com.hectorlopezfernandez.toilet.page;

import org.springframework.context.ApplicationEvent;

/**
 * Signals that the lifecycle of a Page has changed state. This will be listened by
 * some components like the search index, or caches.
 * 
 * @author hector
 */
public class PageLifecycleEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7298257353578892290L;

	private final Type type;

	public PageLifecycleEvent(Page page, Type type) {
		// superclass guarantees non-null tag
		super(page);
		if (type == null) throw new IllegalArgumentException("Type parameter can't be null");
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
		DELETED
	}

}
