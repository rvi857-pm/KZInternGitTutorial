package com.example.pagination.model;

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

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getipDomain() {
		return ipDomain;
	}

	public void setipDomain(String ipDomain) {
		this.ipDomain = ipDomain;
	}

	public String getcity() {
		return city;
	}

	public void setcity(String city) {
		this.city = city;
	}

	public String getstate() {
		return state;
	}

	public void setstate(String state) {
		this.state = state;
	}

	public String getcountry() {
		return country;
	}

	public void setcountry(String country) {
		this.country = country;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getsalesforceId() {
		return salesforceId;
	}

	public void setsalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
	}

	public void setAll(String value) {
		this.name = value;
		this.ipDomain = value;
		this.city = value;
		this.state = value;
		this.country = value;
		this.type = value;
		this.salesforceId = value;
	}

	public boolean isNull() {
		if (this.name == null && this.ipDomain == null && this.city == null && this.state == null
				&& this.country == null && this.type == null && this.salesforceId == null) {
			return true;
		}
		return false;
	}
}
