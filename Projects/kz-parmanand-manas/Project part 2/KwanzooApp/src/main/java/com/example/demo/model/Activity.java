package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "activity")
public class Activity {
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private String id;
	
	@Column(name = "buyer_id")
	private String buyer_id;
	
	@Column(name = "datetime")
	private String datetime;
	
	@Column(name = "activity_type")
	private String activity_type;
	
	@Column(name = "details")
	private String details;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id",  updatable = false, insertable = false)
	@JsonBackReference
	private Buyer buyer;


	public Activity(String id, String buyer_id, String datetime, String activity_type, String details, Buyer buyer) {
		super();
		this.id = id;
		this.buyer_id = buyer_id;
		this.datetime = datetime;
		this.activity_type = activity_type;
		this.details = details;
		this.buyer = buyer;
	}


	public Activity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBuyer_id() {
		return buyer_id;
	}


	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}


	public String getDatetime() {
		return datetime;
	}


	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	public String getActivity_type() {
		return activity_type;
	}


	public void setActivity_type(String activity_type) {
		this.activity_type = activity_type;
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
		return "Activity [id=" + id + ", buyer_id=" + buyer_id + ", datetime=" + datetime + ", activity_type="
				+ activity_type + ", details=" + details + ", buyer=" + buyer + "]";
	}
		
	
}
