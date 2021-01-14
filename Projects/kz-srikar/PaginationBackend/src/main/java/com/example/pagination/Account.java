package com.example.pagination;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Account {
	@Id
	private String id;
	private String account_name;
	private String ip_domain;
	private String ip_geo_city;
	private String ip_geo_state;
	private String ip_geo_country;
	private String type;
	private String sfdc_account_id;
	
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getIp_domain() {
		return ip_domain;
	}
	public void setIp_domain(String ip_domain) {
		this.ip_domain = ip_domain;
	}
	public String getIp_geo_city() {
		return ip_geo_city;
	}
	public void setIp_geo_city(String ip_geo_city) {
		this.ip_geo_city = ip_geo_city;
	}
	public String getIp_geo_state() {
		return ip_geo_state;
	}
	public void setIp_geo_state(String ip_geo_state) {
		this.ip_geo_state = ip_geo_state;
	}
	public String getIp_geo_country() {
		return ip_geo_country;
	}
	public void setIp_geo_country(String ip_geo_country) {
		this.ip_geo_country = ip_geo_country;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSfdc_account_id() {
		return sfdc_account_id;
	}
	public void setSfdc_account_id(String sfdc_account_id) {
		this.sfdc_account_id = sfdc_account_id;
	}
}
