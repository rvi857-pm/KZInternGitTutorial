package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements Serializable {
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="ip_domain")
	private String ip_domain;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="country")
	private String country;
	
	@Column(name="type")
	private String type;
	
	@Column(name="salesforce_id")
	private String salesforce_id;

	@OneToMany(mappedBy = "account", targetEntity = Buyer.class, orphanRemoval = true)
	@JsonManagedReference
	private List<Buyer> buyer_list;

	
	
	public Account(String id, String name, String ip_domain, String city, String state, String country, String type,
			String salesforce_id) {
		super();
		this.id = id;
		this.name = name;
		this.ip_domain = ip_domain;
		this.city = city;
		this.state = state;
		this.country = country;
		this.type = type;
		this.salesforce_id = salesforce_id;
	}

	public Account(String id, String name, String ip_domain, String city, String state, String country, String type,
			String salesforce_id, List<Buyer> buyer_list) {
		super();
		this.id = id;
		this.name = name;
		this.ip_domain = ip_domain;
		this.city = city;
		this.state = state;
		this.country = country;
		this.type = type;
		this.salesforce_id = salesforce_id;
		this.buyer_list = buyer_list;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getIp_domain() {
		return ip_domain;
	}

	public void setIp_domain(String ip_domain) {
		this.ip_domain = ip_domain;
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

	public void setSalesforce_id(String salesforce_id) {
		this.salesforce_id = salesforce_id;
	}

	public List<Buyer> getBuyer_list() {
		return buyer_list;
	}

	public void setBuyer_list(List<Buyer> buyer_list) {
		this.buyer_list = buyer_list;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", ip_domain=" + ip_domain + ", city=" + city + ", state="
				+ state + ", country=" + country + ", type=" + type + ", salesforce_id=" + salesforce_id
				+ ", buyer_list=" + buyer_list + "]";
	}	
	
}
