package com.kwanzoo.project.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PersonaCount implements Serializable {
	
	String jobLevel;
	String jobFunction;
	int count;
	
	
	public PersonaCount(String jobLevel, String jobFunction, int count) {
		super();
		this.jobLevel = jobLevel;
		this.jobFunction = jobFunction;
		this.count = count;
	}


	public String getJobLevel() {
		return jobLevel;
	}


	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}


	public String getJobFunction() {
		return jobFunction;
	}


	public void setJobFunction(String jobFunction) {
		this.jobFunction = jobFunction;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}

}
