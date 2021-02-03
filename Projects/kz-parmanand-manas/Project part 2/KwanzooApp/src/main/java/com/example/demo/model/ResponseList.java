package com.example.demo.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

public class ResponseList implements Serializable{

	List<LinkedHashMap<String, Object>> entity_list;
	long total_count;
	
	public ResponseList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseList(List<LinkedHashMap<String, Object>> entity_list, long total_count) {
		super();
		this.entity_list = entity_list;
		this.total_count = total_count;
	}

	@Override
	public String toString() {
		return "ResponseList [entity_list=" + entity_list + ", total_count=" + total_count + "]";
	}

	public List<LinkedHashMap<String, Object>> getEntity_list() {
		return entity_list;
	}

	public void setEntity_list(List<LinkedHashMap<String, Object>> entity_list) {
		this.entity_list = entity_list;
	}

	public long getTotal_count() {
		return total_count;
	}

	public void setTotal_count(long total_count) {
		this.total_count = total_count;
	}
	
	
	
}
