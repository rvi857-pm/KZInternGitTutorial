package com.kwanzoo.project.model;

import java.io.Serializable;
import java.util.List;

public class PagedReturn<T> implements Serializable {
	
	private static final long serialVersionUID = -1808817245167346917L;
	List<T> content;
	boolean last;
	boolean first;
	int totalPages;
	int number;
	
	public PagedReturn(List<T> content, boolean last, boolean first, int totalPages, int number) {
		super();
		this.content = content;
		this.last = last;
		this.first = first;
		this.totalPages = totalPages;
		this.number = number;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
