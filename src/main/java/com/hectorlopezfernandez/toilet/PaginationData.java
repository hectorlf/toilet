package com.hectorlopezfernandez.toilet;

public class PaginationData {

	public static final String PAGE_PARAMETER_NAME = "page";

	private int page = 1;
	private int size = Constants.DEFAULT_PAGE_SIZE;
	private long total = 0;
	private int lastAvailablePage = 1;
	private boolean enabled = true;
	
	public PaginationData() {
	}
	
	private PaginationData(int page, int size, long total) {
		this.page = page;
		this.size = size < 1 ? 1 : size;
		this.total = total;
		this.lastAvailablePage = computeMaxPage();
	}

	public int getPage() {
		return page;
	}
	public int getSize() {
		return size;
	}
	public long getTotal() {
		return total;
	}
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * Returns the zero-based computed page, suitable for spring data. This will take into account
	 * the page size and total elements, if available.
	 * 
	 * @return the computed page
	 */
	public int getEffectivePage() {
		return (page < 1 ? 1 : (page < lastAvailablePage ? page : lastAvailablePage)) - 1;
	}

	public PaginationData withPage(int page) {
		this.page = page;
		return this;
	}
	public PaginationData withSize(int size) {
		this.size = size < 1 ? 1 : size;;
		return this;
	}
	public PaginationData forTotal(long total) {
		this.total = total < 0 ? 0 : total;
		this.lastAvailablePage = computeMaxPage();
		return this;
	}
	public PaginationData setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public boolean onFirstPage() {
		return page <= 1;
	}
	public boolean onLastPage() {
		return page >= computeMaxPage();
	}
	public PaginationData previous() {
		return new PaginationData(page - 1, size, total);
	}
	public PaginationData next() {
		return new PaginationData(page + 1, size, total);
	}

	private int computeMaxPage() {
		return (int) ((total / size) + (total % size == 0 ? 0 : 1));
	}

}
