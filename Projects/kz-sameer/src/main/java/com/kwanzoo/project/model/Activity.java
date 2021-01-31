package com.kwanzoo.project.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity()
@Table(name = "activity")
public class Activity{
	
	@Id
	Integer id;
	String activityType;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss Z")
	Date datetime;
	String details;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    @JsonIgnore
	Buyer buyer;
	
	public Activity() {
	}

	public Activity(Integer id, String activityType, Date datetime, String details, Buyer buyer) {
		super();
		this.id = id;
		this.activityType = activityType;
		this.datetime = datetime;
		this.details = details;
		this.buyer = buyer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	@Override
	public String toString() {
		return "Activity [id=" + id + ", activityType=" + activityType + ", datetime=" + datetime + ", details="
				+ details + ", buyer=" + buyer + "]";
	}
	
}
