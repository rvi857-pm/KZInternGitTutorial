package com.kwanzoo.app.Utility;

import java.util.List;

public class Metric {

	private float score;
	private boolean qualified;
	private int buyerCount;
	private int activityCount;
	private List<Persona> personaCount;
	private List<Location> locationCount;

	public Metric() {
		super();
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public boolean isQualified() {
		return qualified;
	}

	public void setQualified(boolean qualified) {
		this.qualified = qualified;
	}

	public int getBuyerCount() {
		return buyerCount;
	}

	public void setBuyerCount(int buyerCount) {
		this.buyerCount = buyerCount;
	}

	public int getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(int activityCount) {
		this.activityCount = activityCount;
	}

	public List<Persona> getPersonaCount() {
		return personaCount;
	}

	public void setPersonaCount(List<Persona> personaCount) {
		this.personaCount = personaCount;
	}

	public List<Location> getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(List<Location> locationCount) {
		this.locationCount = locationCount;
	}

}
