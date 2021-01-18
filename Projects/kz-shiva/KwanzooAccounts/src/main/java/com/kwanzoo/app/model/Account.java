package com.kwanzoo.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	private String id;
	private String name;
	private String ipDomain;
	private String city;
	private String state;
	private String country;
	private String type;
	private String salesforceId;

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
