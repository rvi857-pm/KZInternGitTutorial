package com.kwanzoo.project.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class AccountReturn implements Serializable{
	
	String id;
	String name;
	String ipDomain;
	String city;
	String state;
	String country;
	String type;
	String salesforceId;
	Float score;
	Boolean marketingQualified;
	Integer buyerCount;
	ActivityCount activityCount;
	List<PersonaCount> personaCount;
	List<LocationCount> locationCount;
	
	public AccountReturn(String id, String name, String ipDomain, String city, String state, String country,
			String type, String salesforceId, Float score, Boolean marketingQualified, Integer buyerCount
			,ActivityCount activityCount, List<PersonaCount> personaCount, List<LocationCount> locationCount) {
		super();
		this.id = id;
		this.name = name;
		this.ipDomain = ipDomain;
		this.city = city;
		this.state = state;
		this.country = country;
		this.type = type;
		this.salesforceId = salesforceId;
		this.score = score;
		this.marketingQualified = marketingQualified;
		this.buyerCount = buyerCount;
		this.activityCount = activityCount;
		this.personaCount = personaCount;
		this.locationCount = locationCount;
	}

	public AccountReturn() {

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

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Boolean getMarketingQualified() {
		return marketingQualified;
	}

	public void setMarketingQualified(Boolean marketingQualified) {
		this.marketingQualified = marketingQualified;
	}

	public Integer getBuyerCount() {
		return buyerCount;
	}

	public void setBuyerCount(Integer buyerCount) {
		this.buyerCount = buyerCount;
	}

	public ActivityCount getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(ActivityCount activityCount) {
		this.activityCount = activityCount;
	}

	public List<PersonaCount> getPersonaCount() {
		return personaCount;
	}

	public void setPersonaCount(List<PersonaCount> personaCount) {
		this.personaCount = personaCount;
	}

	public List<LocationCount> getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(List<LocationCount> locationCount) {
		this.locationCount = locationCount;
	}

	@Override
	public String toString() {
		return "AccountReturn [id=" + id + ", name=" + name + ", ipDomain=" + ipDomain + ", city=" + city + ", state="
				+ state + ", country=" + country + ", type=" + type + ", salesforceId=" + salesforceId + ", score="
				+ score + ", marketingQualified=" + marketingQualified + ", buyerCount=" + buyerCount
				+ ", activityCount=" + activityCount + ", personaCount=" + personaCount + ", locationCount="
				+ locationCount + "]";
	}
	
}
