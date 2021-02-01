package com.kwanzoo.app.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Account {

	@Id
	private String id;
	private String name;
	private String ip_domain;
	private String city;
	private String state;
	private String country;
	private String type;
	private String salesforce_id;

	@OneToMany(targetEntity = Buyer.class, mappedBy = "account", orphanRemoval = false)
	@JsonManagedReference
	private List<Buyer> buyers;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Buyer> getBuyers() {
		return buyers;
	}

	public void setBuyers(List<Buyer> buyers) {
		this.buyers = buyers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp_domain() {
		return ip_domain;
	}

	public void setIp_domain(String ipDomain) {
		this.ip_domain = ipDomain;
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

	public String getSalesforce_id() {
		return salesforce_id;
	}

	public void setSalesforce_id(String salesforceId) {
		this.salesforce_id = salesforceId;
	}

}
