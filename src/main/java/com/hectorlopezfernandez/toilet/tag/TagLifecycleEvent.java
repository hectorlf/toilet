package com.hectorlopezfernandez.toilet.tag;

import org.springframework.context.ApplicationEvent;

/**
 * Signals that the lifecycle of a Tag has changed state. This will be listened by
 * some components like the search index, or caches.
 * 
 * @author hector
 */
public class TagLifecycleEvent extends ApplicationEvent {

	private static final long serialVersionUID = -4233939260520148102L;

	private final Type type;

	public TagLifecycleEvent(Tag tag, Type type) {
		// superclass guarantees non-null tag
		super(tag);
		if (type == null) throw new IllegalArgumentException("Type parameter can't be null");
		this.type = type;
	}

	@Override
	public Tag getSource() {
		return (Tag) super.getSource();
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
