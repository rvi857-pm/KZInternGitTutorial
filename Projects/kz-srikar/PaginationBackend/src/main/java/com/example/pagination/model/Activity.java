package com.example.pagination.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Activity {
	@Id
	private String id;
	private String buyerId;
	private String datetime;
	private String activityType;
	private String details;
	
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

}
