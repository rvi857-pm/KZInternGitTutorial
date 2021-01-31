package com.kwanzoo.project.model;


import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActivityReturn implements Serializable {

	private static final long serialVersionUID = 1L;
	String activityType;
	@JsonFormat(pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSX")
	Date datetime;
	String details;
	String buyerId;
	
	public ActivityReturn(String activityType, Date datetime, String details, String buyerId) {
		super();
		this.activityType = activityType;
		this.datetime = datetime;
		this.details = details;
		this.buyerId = buyerId;
	}
	
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
}
