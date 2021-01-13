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
	private int Id;
	
	@Column( name = "Account_name")
	private String Account_name;
	
	@Column( name = "IP_Domain")
	private String IP_Domain;
	
	@Column( name = "IP_Geo_City")
	private String IP_Geo_City;
	
	@Column( name = "IP_Geo_Country")
	private String IP_Geo_Country;
	
	@Column( name = "Type")
	private String Type;
	
	@Column( name = "SFDC_Account_ID")
	private String SFDC_Account_ID;
	
	@Column( name = "IP_Geo_State")
	private String IP_Geo_State;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getAccount_name() {
		return Account_name;
	}

	public void setAccount_name(String account_name) {
		Account_name = account_name;
	}

	public String getIP_Domain() {
		return IP_Domain;
	}

	public void setIP_Domain(String iP_Domain) {
		IP_Domain = iP_Domain;
	}

	public String getIP_Geo_City() {
		return IP_Geo_City;
	}

	public void setIP_Geo_City(String iP_Geo_City) {
		IP_Geo_City = iP_Geo_City;
	}

	public String getIP_Geo_Country() {
		return IP_Geo_Country;
	}

	public void setIP_Geo_Country(String iP_Geo_Country) {
		IP_Geo_Country = iP_Geo_Country;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getSFDC_Account_ID() {
		return SFDC_Account_ID;
	}

	public void setSFDC_Account_ID(String sFDC_Account_ID) {
		SFDC_Account_ID = sFDC_Account_ID;
	}

	public String getIP_Geo_State() {
		return IP_Geo_State;
	}

	public void setIP_Geo_State(String iP_Geo_State) {
		IP_Geo_State = iP_Geo_State;
	}
	
	@Override
	public String toString(){
		return " Account_name : " + Account_name +
				"IP_Domain :" + IP_Domain +
				"IP_Geo_City :" + IP_Geo_City +
				"IP_Geo_State :" + IP_Geo_State +
				"IP_Geo_Country :" + IP_Geo_Country +
				"Type :" + Type +
				"SFDC_Account_ID :" + SFDC_Account_ID + "\n";
	}
	
	
	
}
