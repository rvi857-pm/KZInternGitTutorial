package com.kwanzoo.app.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Data {

	private List<Map<String, Object>> data;
	private int totalElements;

	public Data() {
		super();
		this.data = new ArrayList<Map<String, Object>>();
	}

	public void addData(Map<String, Object> data) {
		this.data.add(data);
	}
	
	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

}