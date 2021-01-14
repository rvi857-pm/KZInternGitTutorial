package com.kwanzoo.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	private String id;
	private String accountName;
	private String ipDomain;
	private String ipGeoCity;
	private String ipGeoState;
	private String ipGeoCountry;
	private String type;
	private String sfdcAccountId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getIpDomain() {
		return ipDomain;
	}

	public void setIpDomain(String ipDomain) {
		this.ipDomain = ipDomain;
	}

	public String getIpGeoCity() {
		return ipGeoCity;
	}

	public void setIpGeoCity(String ipGeoCity) {
		this.ipGeoCity = ipGeoCity;
	}

	public String getIpGeoState() {
		return ipGeoState;
	}

	public void setIpGeoState(String ipGeoState) {
		this.ipGeoState = ipGeoState;
	}

	public String getIpGeoCountry() {
		return ipGeoCountry;
	}

	public void setIpGeoCountry(String ipGeoCountry) {
		this.ipGeoCountry = ipGeoCountry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSfdcAccountId() {
		return sfdcAccountId;
	}

	public void setSfdcAccountId(String sfdcAccountId) {
		this.sfdcAccountId = sfdcAccountId;
	}

}
