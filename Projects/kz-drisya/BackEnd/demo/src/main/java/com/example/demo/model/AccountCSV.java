package com.example.demo.model;

import com.opencsv.bean.CsvBindByName;


public class AccountCSV {

	@CsvBindByName(column= "name")
	private String name;
	
	@CsvBindByName(column= "domain")
	private String domain;
	
	@CsvBindByName(column= "city")
	private String city;
	
	@CsvBindByName(column= "state")
	private String state;
	
	@CsvBindByName(column= "country")
	private String country;
	
	@CsvBindByName(column= "type")
	private String type;
	
	@CsvBindByName(column= "salesforce")
	private String salesforce;

	public AccountCSV(String name, String domain, String city, String state, String country, String type,String salesforce) {
		this.name = name;
		this.domain = domain;
		this.city = city;
		this.state = state;
		this.country = country;
		this.type = type;
		this.salesforce = salesforce;
	}

	public AccountCSV() {}
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

	public String getSalesforce() {
		return salesforce;
	}

	public void setSalesforce(String salesforce) {
		this.salesforce = salesforce;
	}
	
	

	
}
