package com.example.demo.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table ( name = "buyer")
public class Buyer {

	@Id
	@Column ( name = "id")
	private String id;
	
	@Column ( name = "account_id")
	private String accountId;
	
	@Column ( name = "city")
	private String city;

	@Column ( name = "state")
	private String state;
	
	@Column ( name = "country")
	private String country;
	
	@Column ( name = "source")
	private String source;
	
	@Column ( name = "job_level")
	private String jobLevel;
	
	@Column ( name = "job_function")
	private String jobFunction;
	
	
	@ManyToOne
	@JoinColumn(name = "account_id", insertable = false, updatable = false)
	@JsonBackReference
	private Account account;
	
	public List<Activity> getActivities() {
		return activities;
	}


	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}


	@OneToMany( targetEntity = Activity.class, mappedBy = "buyer", orphanRemoval = false)
	@JsonManagedReference
	private List<Activity> activities;
	


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAccountId() {
		return accountId;
	}


	public void setAccountId(String accountId) {
		this.accountId = accountId;
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


	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	

}
