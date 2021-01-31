package com.example.demo.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table ( name = "account")
public class Account {

	@Id
	@Column ( name = "id")
	private String id;
	
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

	/**
	 * 
	 * @JsonManagedReference is the forward part of reference - the one that get serialized normally
	 * @JsonBackReference is back part of reference , it will be omitted from the serialization
	 */
	@OneToMany( targetEntity = Buyer.class, mappedBy = "account", orphanRemoval = false)
	@JsonManagedReference
	private List<Buyer> buyers;
	
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
	
	public List<Buyer> getBuyers(){
		return buyers;
	}
	
	public void setBuyers(List<Buyer> buyers) {
		this.buyers = buyers;
	}
	
	public boolean isEmpty() {
		
		if ( this.name == null && this.ipDomain == null && this.city == null && this.country == null && this.state == null && this.salesforceId == null && this.type == null)
			return true;
		return false;
	}
	
	
	
}




