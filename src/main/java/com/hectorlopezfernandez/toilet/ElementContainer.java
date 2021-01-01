package com.hectorlopezfernandez.toilet;

import java.util.List;

public class ElementContainer <E> {

	private List<E> elements;
	private int total;

	// constructors

	public ElementContainer(List<E> elements, int total) {
		if (elements == null) throw new IllegalArgumentException("Parameter elements cannot be null");
		this.elements = elements;
		this.total = total;
	}	

	// getters & setters

	public List<E> getElements() {
		return elements;
	}

	public int getTotal() {
		return total;
	}

}
