package com.example.demo.model;

import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table ( name = "account")
public class Account {

	@Id
	@Column ( name = "id")
	private UUID id;
	
	@Column( name = "name")
	private String name;
	
	@Column( name = "ip_domain")
	private String ipDomain;
	
	@Column( name = "city")
	private String city;
	
	@Column( name = "country")
	private String country;
	
	@Column( name = "type")
	private String type;
	
	@Column( name = "salesforce_id")
	private String salesforceId;
	
	@Column( name = "state")
	private String state;
	
	
	public Account() {}
	
	public Account(String search) { 
		
		this.name = search;
		this.ipDomain = search;
		this.city = search;
		this.country = search;
		this.type = search;
		this.salesforceId = search;
		this.state = search;
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

	public String getSalesforceId() {
		return salesforceId;
	}

	public void setSalesforceId(String salesforceId) {
		this.salesforceId = salesforceId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	
	}
	
	public boolean isEmpty() {
		
		if ( this.name == null && this.ipDomain == null && this.city == null && this.country == null && this.state == null && this.salesforceId == null && this.type == null)
			return true;
		return false;
	}
	
}




