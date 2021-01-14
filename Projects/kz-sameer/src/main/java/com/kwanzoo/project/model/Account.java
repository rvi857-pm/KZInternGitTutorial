package com.kwanzoo.project.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name = "accounts")
public class Account {

	@Id
	UUID id;
	String name;
	String domain;
	String city;
	String state;
	String country;
	String type;
	String accountId;
	
	public Account() {
		
	}

	public Account(UUID id, String name, String domain, String city, String state, String country, String type,
			String accountId) {
		super();
		this.id = id;
		this.name = name;
		this.domain = domain;
		this.city = city;
		this.state = state;
		this.country = country;
		this.type = type;
		this.accountId = accountId;
	}
	
	public void setALL(String any) {
		this.name = any;
		this.domain = any;
		this.city = any;
		this.state = any;
		this.country = any;
		this.type = any;
		this.accountId = any;
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

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	

	
}
