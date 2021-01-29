package com.kwanzoo.project.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BuyerReturn {
	
	String id;
	String city;
	String state;
	String country;
	String source;
	String jobLevel;
	String jobFunction;
	String account_id;
	String account_name;
	Integer score;
	Boolean marketingQualified;
	ActivityCount activityCount;
	
	public BuyerReturn(String id, String city, String state, String country, String source, String jobLevel,
			String jobFunction, String account_id, String account_name, Integer score, Boolean marketingQualified,
			ActivityCount activityCount) {
		super();
		this.id = id;
		this.city = city;
		this.state = state;
		this.country = country;
		this.source = source;
		this.jobLevel = jobLevel;
		this.jobFunction = jobFunction;
		this.account_id = account_id;
		this.account_name = account_name;
		this.score = score;
		this.marketingQualified = marketingQualified;
		this.activityCount = activityCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getMarketingQualified() {
		return marketingQualified;
	}

	public void setMarketingQualified(Boolean marketingQualified) {
		this.marketingQualified = marketingQualified;
	}

	public ActivityCount getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(ActivityCount activityCount) {
		this.activityCount = activityCount;
	}
	
}
