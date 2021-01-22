package com.kwanzoo.project.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity()
@Table(name = "activity")
public class Activity {
	
	@Id
	int id;
	String activityType;
	long datetime;
	String creativeName;
	String websiteUrl;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    @JsonIgnore
	Buyer buyer;
	
	public Activity() {
	}

	public Activity(int id, String activity_type, long datetime, String creativeName, String websiteUrl, Buyer buyer) {
		super();
		this.id = id;
		this.activityType = activity_type;
		this.datetime = datetime;
		this.creativeName = creativeName;
		this.websiteUrl = websiteUrl;
		this.buyer = buyer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActivity_type() {
		return activityType;
	}

	public void setActivity_type(String activity_type) {
		this.activityType = activity_type;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
	}

	public String getCreativeName() {
		return creativeName;
	}

	public void setCreativeName(String creativeName) {
		this.creativeName = creativeName;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	
}
