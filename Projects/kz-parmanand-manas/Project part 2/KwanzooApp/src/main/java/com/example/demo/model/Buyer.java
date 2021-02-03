package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "buyer")
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private String id;
    
    @Column(name="account_id")
    private String account_id;
    
    @Column(name="city")
    private String city;
    
    @Column(name="state")
    private String state;
    
    @Column(name="country")
    private String country;
    
    @Column(name="source")
    private String source;
    
    @Column(name="job_level")
    private String job_level;

    @Column(name="job_function")
    private String job_function;
    
	@ManyToOne
	@JoinColumn(name = "account_id",  updatable = false, insertable = false)
	@JsonBackReference
	private Account account;
	
	@OneToMany(mappedBy = "buyer", targetEntity = Activity.class, cascade = CascadeType.ALL, orphanRemoval = false)
	@JsonManagedReference
	List<Activity> activity_list;

	public Buyer(String id, String account_id, String city, String state, String country, String source,
			String job_level, String job_function, Account account, List<Activity> activity_list) {
		super();
		this.id = id;
		this.account_id = account_id;
		this.city = city;
		this.state = state;
		this.country = country;
		this.source = source;
		this.job_level = job_level;
		this.job_function = job_function;
		this.account = account;
		this.activity_list = activity_list;
	}

	public Buyer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getJob_level() {
		return job_level;
	}

	public void setJob_level(String job_level) {
		this.job_level = job_level;
	}

	public String getJob_function() {
		return job_function;
	}

	public void setJob_function(String job_function) {
		this.job_function = job_function;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Activity> getActivity_list() {
		return activity_list;
	}

	public void setActivity_list(List<Activity> activity_list) {
		this.activity_list = activity_list;
	}

	@Override
	public String toString() {
		return "Buyer [id=" + id + ", account_id=" + account_id + ", city=" + city + ", state=" + state + ", country="
				+ country + ", source=" + source + ", job_level=" + job_level + ", job_function=" + job_function
				+ ", account=" + account + ", activity_list=" + activity_list + "]";
	}

	 
	
    

}
