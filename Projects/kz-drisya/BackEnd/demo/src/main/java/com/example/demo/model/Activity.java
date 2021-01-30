package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table ( name = "activity" )
public class Activity {

	@Id
	@Column ( name = "id" )
	private String id;
	
	@Column ( name = "buyer_id")
	private String buyerId;
	
	@Column ( name = "datetime")
	private String datetime;
	
	@Column ( name = "activity_type")
	private String activityType;
	
	@Column ( name = "details")
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


	public Buyer getBuyer() {
		return buyer;
	}


	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
	
	
}
