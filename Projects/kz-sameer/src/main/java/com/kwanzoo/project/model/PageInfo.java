package com.kwanzoo.project.model;

import java.util.Arrays;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class PageInfo {
	
	Integer pageSize;
	Integer page;
	@DateTimeFormat(pattern = "MMddyyyy")
	Date start;
	@DateTimeFormat(pattern = "MMddyyyy")
	Date end;
	String[] metrics;
	String any;
	String[] exclude;
	
	public PageInfo(Integer pageSize, Integer page, Date start, Date end, String[] metrics, String any,
			String[] exclude) {
		super();
		this.pageSize = pageSize;
		this.page = page;
		this.start = start;
		this.end = end;
		this.metrics = metrics;
		this.any = any;
		this.exclude = exclude;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String[] getMetrics() {
		return metrics;
	}

	public void setMetrics(String[] metrics) {
		this.metrics = metrics;
	}

	public String getAny() {
		return any;
	}

	public void setAny(String any) {
		this.any = any;
	}

	public String[] getExclude() {
		return exclude;
	}

	public void setExclude(String[] exclude) {
		this.exclude = exclude;
	}

	@Override
	public String toString() {
		return "PageInfo [pageSize=" + pageSize + ", page=" + page + ", start=" + start + ", end=" + end + ", metrics="
				+ Arrays.toString(metrics) + ", any=" + any + ", exclude=" + Arrays.toString(exclude) + "]";
	}

}
