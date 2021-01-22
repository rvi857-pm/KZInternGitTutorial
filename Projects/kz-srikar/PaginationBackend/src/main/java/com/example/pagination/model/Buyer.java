package com.example.pagination.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Buyer {
	@Id
	private String id;
	private String accountId;
	private String city;
	private String state;
	private String country;
	private String jobLevel;
	private String jobFunction;
	
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	public String getJobFunction() {
		return jobFunction;
	}
	public void setJobFunction(String jobFunction) {
		this.jobFunction = jobFunction;
	}

}
