package com.example.pagination.model;

import java.util.List;
import java.util.Map;

public class PageResponse {

	private List<Map<String, Object>> content;
	private int totalElements;

	public List<Map<String, Object>> getContent() {
		return content;
	}

	public void setContent(List<Map<String, Object>> content) {
		this.content = content;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

}
