package com.kwanzoo.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	private String id;
	@Column(name = "name")
	private String accountName;
	@Column(name = "ip_domain")
	private String ipDomain;
	@Column(name = "city")
	private String ipGeoCity;
	@Column(name = "state")
	private String ipGeoState;
	@Column(name = "country")
	private String ipGeoCountry;
	@Column(name = "type")
	private String type;
	@Column(name = "salesforce_id")
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
