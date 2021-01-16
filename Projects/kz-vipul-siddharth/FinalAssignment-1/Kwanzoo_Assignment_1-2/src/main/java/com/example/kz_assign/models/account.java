package com.example.kz_assign.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="acc", schema="kwanzoo_assignments")
public class account{
	
	@Column(name="name")
	private String name;
	
	@Column(name="ip_domain")
	private String ip_domain;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="type")
	private String type;
	
	@Column(name="country")
	private String country;
	
	@Column(name="salesforce_id")
	private String salesforce_id;
	
	@Id
	@Column(name="id")
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
//	
//	public String getAccountName() {
//		return AccountName;
//	}
//	public void setAccountName(String accountName) {
//		AccountName = accountName;
//	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSalesforce_id() {
		return salesforce_id;
	}
	public void setSalesforce_id(String salesforce_id) {
		this.salesforce_id = salesforce_id;
	}
}
