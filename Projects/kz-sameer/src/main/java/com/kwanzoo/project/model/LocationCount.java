package com.kwanzoo.project.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LocationCount implements Serializable{
	
	String city;
	String state;
	String country;
	int count;
	
	public LocationCount(String city, String state, String country, int count) {
		super();
		this.city = city;
		this.state = state;
		this.country = country;
		this.count = count;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
