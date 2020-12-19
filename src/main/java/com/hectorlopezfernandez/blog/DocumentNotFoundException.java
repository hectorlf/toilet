package com.hectorlopezfernandez.blog;

/**
 * Thrown when a document doesn't exist in the DB, but the business
 * logic expected one
 */
public final class DocumentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7271205679477404347L;

	public DocumentNotFoundException() {
		// No args constructor
	}

	public DocumentNotFoundException(String message) {
		super(message);
	}

}
