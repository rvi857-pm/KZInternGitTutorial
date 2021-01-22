package com.example.kz_assign.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="buyer", schema="roughwork")
public class buyer {
	@Id
	@Column(name="id")
	String buyer_id;
	
	@Column(name="account_id")
	String accountid;
	
	@Column(name="city")
	String city;
	
	@Column(name="state")
	String state;
	
	@Column(name="country")
	String country;
	
	@Column(name="source")
	String source;
	
	@Column(name="job_level")
	String job_level;
	
	@Column(name="job_function")
	String job_function;
	
	@JsonManagedReference
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "account_id",  insertable = false, updatable = false)
    private account acc;

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getAccountid() {
		return accountid;
	}

	public void setAccountid(String accountid) {
		this.accountid = accountid;
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

	public String getJob_level() {
		return job_level;
	}

	public void setJob_level(String job_level) {
		this.job_level = job_level;
	}

	public String getJob_function() {
		return job_function;
	}

	public void setJob_function(String job_function) {
		this.job_function = job_function;
	}

	public account getAcc() {
		return acc;
	}

	public void setAcc(account acc) {
		this.acc = acc;
	}
	
	
	
}
