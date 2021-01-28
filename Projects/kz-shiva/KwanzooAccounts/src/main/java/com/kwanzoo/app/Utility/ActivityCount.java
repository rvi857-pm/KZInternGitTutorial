package com.kwanzoo.app.Utility;

public class ActivityCount {
	private int ad_clicks;
	private int website_visits;
	private int form_fills;
	private int live_chats;
	private int total;

	public ActivityCount(int ad_clicks, int website_visits, int form_fills, int live_chats, int total) {
		super();
		this.ad_clicks = ad_clicks;
		this.website_visits = website_visits;
		this.form_fills = form_fills;
		this.live_chats = live_chats;
		this.total = total;
	}

	public void incrementAdClicks(int value) {
		this.ad_clicks += value;
		this.total += value;
	}

	public void incrementWebsiteVisits(int value) {
		this.website_visits += value;
		this.total += value;
	}

	public void incrementFormFills(int value) {
		this.form_fills += value;
		this.total += value;
	}

	public void incrementLiveChats(int value) {
		this.live_chats += value;
		this.total += value;
	}

	public int getAd_clicks() {
		return ad_clicks;
	}

	public void setAd_clicks(int ad_clicks) {
		this.ad_clicks = ad_clicks;
	}

	public int getWebsite_visits() {
		return website_visits;
	}

	public void setWebsite_visits(int website_visits) {
		this.website_visits = website_visits;
	}

	public int getForm_fills() {
		return form_fills;
	}

	public void setForm_fills(int form_fills) {
		this.form_fills = form_fills;
	}

	public int getLive_chats() {
		return live_chats;
	}

	public void setLive_chats(int live_chats) {
		this.live_chats = live_chats;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
