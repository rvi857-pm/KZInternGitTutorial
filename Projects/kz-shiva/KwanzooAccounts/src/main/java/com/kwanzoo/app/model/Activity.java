package com.kwanzoo.app.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Activity {

	@Id
	private String id;
	
	@Column(name = "date_time")
	private Date dateTime;

	@Column(name = "activity_type")
	private String activityType;

	private String details;

	@ManyToOne
	@JoinColumn(name = "buyer_id", insertable = false, updatable = false)
	@JsonBackReference
	private Buyer buyer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date datetime) {
		this.dateTime = datetime;
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

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

}
