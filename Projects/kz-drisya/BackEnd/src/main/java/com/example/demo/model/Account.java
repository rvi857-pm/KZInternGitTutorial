package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table ( name = "account")
public class Account {

	@Id
	@Column ( name = "Id")
	private int id;
	
	@Column( name = "Account_name")
	private String name;
	
	@Column( name = "IP_Domain")
	private String domain;
	
	@Column( name = "IP_Geo_City")
	private String city;
	
	@Column( name = "IP_Geo_Country")
	private String country;
	
	@Column( name = "Type")
	private String type;
	
	@Column( name = "SFDC_Account_ID")
	private String sfdc;
	
	@Column( name = "IP_Geo_State")
	private String state;

	
	
	




	public int getId() {
		return id;
	}





	public void setId(int id) {
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





	public String getSfdc() {
		return sfdc;
	}





	public void setSfdc(String sfdc) {
		this.sfdc = sfdc;
	}





	public String getState() {
		return state;
	}





	public void setState(String state) {
		this.state = state;
	}

		
	
	
}
