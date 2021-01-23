package com.example.kz_assign.models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="activity", schema="roughwork")
public class activity {
	@Id
	@Column(name="id")
	String activity_id;
	
	@Column(name="buyer_id")
	String buyer_id;
	
	@Column(name="details")
	String details;
	
	@Column(name="activity_type")
	String type;
	
	@Column(name="datetime")
	Timestamp date;
	
	@JsonManagedReference
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "buyer_id",  insertable = false, updatable = false)
    private buyer activity_buyer;

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public buyer getActivity_buyer() {
		return activity_buyer;
	}

	public void setActivity_buyer(buyer activity_buyer) {
		this.activity_buyer = activity_buyer;
	}


}
