package com.kwanzoo.app.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.Utility.ActivityCount;
import com.kwanzoo.app.Utility.CheckDate;
import com.kwanzoo.app.Utility.Metric;
import com.kwanzoo.app.Utility.Parse;
import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.model.Activity;
import com.kwanzoo.app.model.Buyer;

import javafx.util.Pair;

@Component
public class MetricService {

	@Autowired
	private Parse parse;

	public CheckDate getCheckDate(Map<String, String> filter) {

		CheckDate sample = new CheckDate();
		if (filter.get("start") != null) {
			String date = filter.get("start");
			int year = Integer.parseInt(date.substring(4, 8)) - 1900;
			int month = Integer.parseInt(date.substring(2, 4)) - 1;
			int day = Integer.parseInt(date.substring(0, 2));
			@SuppressWarnings("deprecation")
			Date first = new Date(year, month, day);
			sample.setStartDate(first);
		}
		if (filter.get("end") != null) {
			String date = filter.get("end");
			int year = Integer.parseInt(date.substring(4, 8)) - 1900;
			int day = Integer.parseInt(date.substring(2, 4)) - 1;
			int month = Integer.parseInt(date.substring(0, 2));
			@SuppressWarnings("deprecation")
			Date end = new Date(year, month, day);
			sample.setEndDate(end);
		}
		return sample;

	}

	private void fillMetrics(Map<String, Object> value, String val, Metric metric, boolean flag) {
		if (val.contains("score") || val.contains("all"))
			value.put("score", metric.getScore());

		if (val.contains("marketing_qualified") || val.contains("all"))
			value.put("marketing_qualified", metric.isQualified());

		if (val.contains("activity_count") || val.contains("all"))
			value.put("activity_count", metric.getActivityCount());

		if (flag & (val.contains("buyer_count") || val.contains("all")))
			value.put("buyer_count", metric.getBuyerCount());

		if (flag & (val.contains("persona_count") || val.contains("all")))
			value.put("persona_count", metric.getPersonaCount());

		if (flag & (val.contains("location_count") || val.contains("all")))
			value.put("location_count", metric.getLocationCount());
	}

	public Map<String, Object> fillValues(Map<String, String> filter, Buyer buyer, Metric metric) {
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("id", buyer.getId());
		value.put("account_id", buyer.getAccount().getId());
		value.put("account_name", buyer.getAccount().getName());
		value.put("job_level", buyer.getJobLevel());
		value.put("job_function", buyer.getJobFunction());
		value.put("city", buyer.getCity());
		value.put("state", buyer.getState());
		value.put("country", buyer.getCountry());
		value.put("source", buyer.getSource());

		if (filter.get("metrics") != null) {
			String val = filter.get("metrics");
			fillMetrics(value, val, metric, false);
		}

		return value;
	}

	public Map<String, Object> fillValues(Map<String, String> filter, Account account, Metric metric) {

		Map<String, Object> value = new HashMap<String, Object>();
		value.put("id", account.getId());
		value.put("name", account.getName());
		value.put("ip_domain", account.getIp_domain());
		value.put("city", account.getCity());
		value.put("state", account.getState());
		value.put("country", account.getCountry());
		value.put("type", account.getType());
		value.put("salesforce_id", account.getSalesforce_id());

		if (filter.get("metrics") != null) {
			String val = filter.get("metrics");
			fillMetrics(value, val, metric, true);
		}

		return value;
	}

	public Map<String, Object> fillValues(Map<String, String> filter, Activity activity) {

		Map<String, Object> value = new HashMap<String, Object>();
		value.put("buyer_id", activity.getBuyer().getId());
		value.put("datetime", activity.getDateTime());
		value.put("activity_type", activity.getActivityType());
		value.put("details", activity.getDetails());

		return value;
	}

	private float getBuyerScore(Buyer buyer, CheckDate date, ActivityCount activityCount) {
		float buyerScore = 0;
		List<Activity> activities = buyer.getActivities();

		for (int j = 0; j < activities.size(); j++) {

			if (date.getStartDate() != null) {
				if (activities.get(j).getDateTime().compareTo(date.getStartDate()) < 0)
					continue;
			}

			if (date.getEndDate() != null) {
				if (activities.get(j).getDateTime().compareTo(date.getEndDate()) > 0)
					continue;
			}

			if (activities.get(j).getActivityType().equals("Ad Click")) {
				buyerScore += 1;
				activityCount.incrementAdClicks(1);
			} else if (activities.get(j).getActivityType().equals("Website Visit")) {
				buyerScore += 0.1;
				activityCount.incrementWebsiteVisits(1);
			} else if (activities.get(j).getActivityType().equals("Form Fill")) {
				buyerScore += 3;
				activityCount.incrementFormFills(1);
			} else if (activities.get(j).getActivityType().equals("Live Chat")) {
				buyerScore += 3;
				activityCount.incrementLiveChats(1);
			} else
				continue;
		}

		return buyerScore;
	}

	@Cacheable(value = "accounts", key = "{#id, #start, #end}")
	public Metric getMetrics(Account account, CheckDate date, String id, String start, String end) {

		Map<Pair<String, String>, Integer> personaStore = new HashMap<Pair<String, String>, Integer>();
		Map<Pair<String, Pair<String, String>>, Integer> locationStore = new HashMap<Pair<String, Pair<String, String>>, Integer>();

		int count = 0;
		ActivityCount activityCount = new ActivityCount(0, 0, 0, 0, 0);

		float accountScore = 0;
		List<Buyer> buyers = account.getBuyers();

		for (int i = 0; i < buyers.size(); i++) {

			Buyer buyer = buyers.get(i);
			float buyerScore = getBuyerScore(buyer, date, activityCount);

			if (buyer.getJobLevel().equals("C-Level"))
				buyerScore *= 2;
			else if (buyer.getJobLevel().equals("Owner,Board Member"))
				buyerScore *= 1.75;
			else if (buyer.getJobLevel().equals("VP,Director"))
				buyerScore *= 1.5;
			else if (buyer.getJobLevel().equals("Manager"))
				buyerScore *= 1.25;
			else
				buyerScore *= 1;

			if (buyerScore >= 4)
				count++;

			accountScore += buyerScore;

			Pair<String, String> newPair = new Pair<String, String>(buyers.get(i).getJobLevel(),
					buyers.get(i).getJobFunction());
			personaStore.put(newPair, personaStore.containsKey(newPair) ? personaStore.get(newPair) + 1 : 1);

			Pair<String, String> tempPair = new Pair<String, String>(buyers.get(i).getState(),
					buyers.get(i).getCountry());
			Pair<String, Pair<String, String>> locPair = new Pair<String, Pair<String, String>>(buyers.get(i).getCity(),
					tempPair);
			locationStore.put(locPair, locationStore.containsKey(locPair) ? locationStore.get(locPair) + 1 : 1);
		}

		Metric metric = new Metric();
		boolean flag = count >= 3 ? true : false;

		metric.setScore(accountScore);
		metric.setQualified(flag);
		metric.setBuyerCount(buyers.size());
		metric.setActivityCount(activityCount);
		metric.setPersonaCount(parse.getPersonas(personaStore));
		metric.setLocationCount(parse.getLocations(locationStore));

		return metric;

	}

	@Cacheable(value = "buyers", key = "{#id, #start, #end}")
	public Metric getMetrics(Buyer buyer, CheckDate date, String id, String start, String end) {

		ActivityCount activityCount = new ActivityCount(0, 0, 0, 0, 0);

		float buyerScore = getBuyerScore(buyer, date, activityCount);

		Metric metric = new Metric();
		boolean flag = buyerScore >= 4 ? true : false;

		metric.setScore(buyerScore);
		metric.setQualified(flag);
		metric.setActivityCount(activityCount);

		return metric;

	}
}
