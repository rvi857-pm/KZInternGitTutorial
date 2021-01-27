package com.kwanzoo.app.Utility;

public class Metric {

	private float score;
	private boolean qualified;
	private int buyerCount;
	private int activityCount;
	private Persona personaCount;
	private Location locationCount;

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

	public Persona getPersonaCount() {
		return personaCount;
	}

	public void setPersonaCount(Persona personaCount) {
		this.personaCount = personaCount;
	}

	public Location getLocationCount() {
		return locationCount;
	}

	public void setLocationCount(Location locationCount) {
		this.locationCount = locationCount;
	}

}
