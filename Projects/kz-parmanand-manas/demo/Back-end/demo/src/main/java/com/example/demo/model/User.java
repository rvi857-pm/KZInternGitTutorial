package com.example.demo.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Entities(User) class

@Entity
@Table(name = "account")
public class User {

	//attributes
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private String id;
	
	@Column(name="name")
	private String Account_Name;
	
	@Column(name="ip_domain")
	private String Ip_Domain;

	@Column(name="city")
	private String Ip_Geo_City;
	
	@Column(name="state")
	private String Ip_Geo_State;
	
	@Column(name="country")
	private String Ip_Geo_Country;
	
	@Column(name="type")
	private String Types;
	
	@Column(name="salesforce_id")
	private String SFDC_Account_Id;
	
	//constructors
	public User(String id, String account_Name, String ip_Domain, String ip_Geo_City, String ip_Geo_State,
			String ip_Geo_Country, String types, String sFDC_Account_Id) {
		super();
		this.id = id;
		Account_Name = account_Name;
		Ip_Domain = ip_Domain;
		Ip_Geo_City = ip_Geo_City;
		Ip_Geo_State = ip_Geo_State;
		Ip_Geo_Country = ip_Geo_Country;
		Types = types;
		SFDC_Account_Id = sFDC_Account_Id;
	}
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//getters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount_Name() {
		return Account_Name;
	}

	public void setAccount_Name(String account_Name) {
		Account_Name = account_Name;
	}

	public String getIp_Domain() {
		return Ip_Domain;
	}

	//setters
	
	public void setIp_Domain(String ip_Domain) {
		Ip_Domain = ip_Domain;
	}

	public String getIp_Geo_City() {
		return Ip_Geo_City;
	}

	public void setIp_Geo_City(String ip_Geo_City) {
		Ip_Geo_City = ip_Geo_City;
	}

	public String getIp_Geo_State() {
		return Ip_Geo_State;
	}

	public void setIp_Geo_State(String ip_Geo_State) {
		Ip_Geo_State = ip_Geo_State;
	}

	public String getIp_Geo_Country() {
		return Ip_Geo_Country;
	}

	public void setIp_Geo_Country(String ip_Geo_Country) {
		Ip_Geo_Country = ip_Geo_Country;
	}

	public String getTypes() {
		return Types;
	}

	public void setTypes(String types) {
		Types = types;
	}

	public String getSFDC_Account_Id() {
		return SFDC_Account_Id;
	}

	public void setSFDC_Account_Id(String sFDC_Account_Id) {
		SFDC_Account_Id = sFDC_Account_Id;
	}

	
	//toString() helpful in debugging(just by printing attributes of user object) 
	@Override
	public String toString() {
		return "User [id=" + id + ", Account_Name=" + Account_Name + ", Ip_Domain=" + Ip_Domain + ", Ip_Geo_City="
				+ Ip_Geo_City + ", Ip_Geo_State=" + Ip_Geo_State + ", Ip_Geo_Country=" + Ip_Geo_Country + ", Types="
				+ Types + ", SFDC_Account_Id=" + SFDC_Account_Id + "]";
	}
}
