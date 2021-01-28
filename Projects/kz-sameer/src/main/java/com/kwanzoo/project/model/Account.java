package com.kwanzoo.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity(name = "accounts")
public class Account{

	@Id
	String id;
	String name;
	String ipDomain;
	String city;
	String state;
	String country;
	String type;
	String salesforceId;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "account")
	@JsonIgnore
	private List<Buyer> buyers;
	
	public Account() {
		
	}
	
	public void setAll(String any) {
		this.id = any;
		this.name = any;
		this.ipDomain = any;
		this.city = any;
		this.state = any;
		this.country = any;
		this.type = any;
		this.salesforceId = any;
	}
	

	public Account(String id, String name, String ipDomain, String city, String state, String country, String type,
			String salesforceId, List<Buyer> buyers) {
		super();
		this.id = id;
		this.name = name;
		this.ipDomain = ipDomain;
		this.city = city;
		this.state = state;
		this.country = country;
		this.type = type;
		this.salesforceId = salesforceId;
		this.buyers = buyers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public List<Buyer> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<Buyer> buyers) {
		this.buyers = buyers;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", ipDomain=" + ipDomain + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", type=" + type + ", salesforceId=" + salesforceId + ", buyers=" + buyers
				+ "]";
	}
	
}
