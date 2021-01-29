package com.kwanzoo.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity()
@Table(name = "buyer")

public class Buyer implements Serializable{
	
	@Id
	String id;
	String city;
	String state;
	String country;
	String source;
	String jobLevel;
	String jobFunction;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
	private Account account;
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "buyer")
	private List<Activity> activities;
	
	public Buyer() {
		
	}
	
	public Buyer(String any) {
		
		this.id = any;
		this.city = any;
		this.state = any;
		this.country = any;
		this.source = any;
		this.jobLevel = any;
		this.jobFunction = any;
		
	}

	public Buyer(String id, String city, String state, String country, String source, String jobLevel,
			String jobFunction, Account account, List<Activity> activities) {
		super();
		this.id = id;
		this.city = city;
		this.state = state;
		this.country = country;
		this.source = source;
		this.jobLevel = jobLevel;
		this.jobFunction = jobFunction;
		this.account = account;
		this.activities = activities;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	@Override
	public String toString() {
		return "Buyer [id=" + id + ", city=" + city + ", state=" + state + ", country=" + country + ", source=" + source
				+ ", jobLevel=" + jobLevel + ", jobFunction=" + jobFunction + ", account=" + account + ", activities="
				+ activities + "]";
	}
	
}
