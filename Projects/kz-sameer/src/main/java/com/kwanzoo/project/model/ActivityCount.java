package com.kwanzoo.project.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActivityCount implements Serializable {
	
	int adClicks;
	int websiteVisits;
	int formFills;
	int liveChats;
	int total;
	
	public ActivityCount(int adClicks, int websiteVisits, int formFills, int liveChats, int total) {
		super();
		this.adClicks = adClicks;
		this.websiteVisits = websiteVisits;
		this.formFills = formFills;
		this.liveChats = liveChats;
		this.total = total;
	}

	public ActivityCount(int adClicks, int websiteVisits, int formFills, int liveChats) {
		super();
		this.adClicks = adClicks;
		this.websiteVisits = websiteVisits;
		this.formFills = formFills;
		this.liveChats = liveChats;
		this.total = adClicks + websiteVisits + formFills + liveChats;
	}
	
	public int getAdClicks() {
		return adClicks;
	}

	public void setAdClicks(int adClicks) {
		this.adClicks = adClicks;
	}

	public int getWebsiteVisits() {
		return websiteVisits;
	}

	public void setWebsiteVisits(int websiteVisits) {
		this.websiteVisits = websiteVisits;
	}

	public int getFormFills() {
		return formFills;
	}

	public void setFormFills(int formFills) {
		this.formFills = formFills;
	}

	public int getLiveChats() {
		return liveChats;
	}

	public void setLiveChats(int liveChats) {
		this.liveChats = liveChats;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
