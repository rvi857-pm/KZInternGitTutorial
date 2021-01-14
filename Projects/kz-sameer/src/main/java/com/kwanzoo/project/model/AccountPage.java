package com.kwanzoo.project.model;


public class AccountPage extends Account {
	
	Integer page;
	Integer pageSize;
	String any;
	
	public AccountPage(Integer page, Integer pageSize, String any) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.any = any;
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getAny() {
		return any;
	}
	public void setAny(String any) {
		this.any = any;
	}
	
	
}
