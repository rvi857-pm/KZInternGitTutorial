package com.kwanzoo.project.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "accounts")
public class Account {

	@Id
	UUID id;
	String name;
	String ipDomain;
	String city;
	String state;
	String country;
	String type;
	String salesforceId;
	
	public Account() {
		
	}
	
	
	
	public Account(UUID id, String name, String ipDomain, String city, String state, String country, String type,
			String salesforceId) {
		super();
		this.id = id;
		this.name = name;
		this.ipDomain = ipDomain;
		this.city = city;
		this.state = state;
		this.country = country;
		this.type = type;
		this.salesforceId = salesforceId;
	}



	public void setALL(String any) {
		this.name = any;
		this.ipDomain = any;
		this.city = any;
		this.state = any;
		this.country = any;
		this.type = any;
		this.salesforceId = any;
	}



	public UUID getId() {
		return id;
	}



	public void setId(UUID id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getIpDomain() {
		return ipDomain;
	}



	public void setIpDomain(String ipDomain) {
		this.ipDomain = ipDomain;
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



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getSalesforceId() {
		return salesforceId;
	}



	public void setSalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
	}
	

	
}
