package com.example.pagination.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.pagination.model.Account;
import com.example.pagination.model.Activity;
import com.example.pagination.model.Buyer;

@Service
public class RedisCache {
	private void calculate(List<Buyer> buyers, Map<String, Object> contentItem) {

		// score and marketing qualified
		float score = 0;
		float numOfBuyersQualified = 0;

		// activity count
		int adClicks, websiteVisits, formFills, liveChats;
		adClicks = websiteVisits = formFills = liveChats = 0;

		// persona count
		Map<String, Map<String, Object>> personas = new HashMap<>();

		// location count
		Map<String, Map<String, Object>> locations = new HashMap<>();

		for (int i = 0; i < buyers.size(); i++) {
			Buyer buyer = buyers.get(i);
			List<Activity> activities = buyer.getActivities();
			float activityScore = 0;
			for (int j = 0; j < activities.size(); j++) {
				Activity activity = activities.get(j);
				if (activity.getActivityType().equals("ad click")) {
					activityScore += 1;
				} else if (activity.getActivityType().equals("Website Visit")) {
					activityScore += 0.1;
				} else if (activity.getActivityType().equals("Form Fill")) {
					activityScore += 3;
				} else if (activity.getActivityType().equals("Live chat")) {
					activityScore += 3;
				}
				// activity count
				if (activity.getActivityType() == "ad click") {
					adClicks += 1;
				} else if (activity.getActivityType().equals("Website Visit")) {
					websiteVisits += 1;
				} else if (activity.getActivityType().equals("Form Fill")) {
					formFills += 1;
				} else if (activity.getActivityType().equals("Live chat")) {
					liveChats += 1;
				}
			}
			if (buyer.getJobLevel().equals("C-Level")) {
				activityScore *= 2;
			} else if (buyer.getJobLevel().equals("Owner,Board Member")) {
				activityScore *= 1.75;
			} else if (buyer.getJobLevel().equals("VP,Director")) {
				activityScore *= 1.5;
			} else if (buyer.getJobLevel().equals("Manager")) {
				activityScore *= 1.25;
			}
			if (activityScore >= 4) {
				numOfBuyersQualified += 1;
			}
			score += activityScore;

			// persona count
			if (!(buyer.getJobLevel().equals("") && buyer.getJobFunction().equals(""))) {

				String key = buyer.getJobLevel() + "$" + buyer.getJobFunction();
				if (personas.containsKey(key)) {
					Map<String, Object> persona = personas.get(key);
					persona.replace("count", (int) persona.get("count") + 1);
					personas.replace(key, persona);
				} else {
					Map<String, Object> persona = new HashMap<>();
					persona.put("job_level", buyer.getJobLevel());
					persona.put("job_function", buyer.getJobFunction());
					persona.put("count", 1);
					personas.put(key, persona);
				}
			}

			// location count
			if (!(buyer.getCity().equals("") && buyer.getState().equals("") && buyer.getCountry().equals(""))) {
				String key = buyer.getCity() + "$" + buyer.getState() + "$" + buyer.getCountry();
				if (locations.containsKey(key)) {
					Map<String, Object> location = locations.get(key);
					location.replace("count", (int) location.get("count") + 1);
					locations.replace(key, location);
				} else {
					Map<String, Object> location = new HashMap<>();
					location.put("city", buyer.getCity());
					location.put("state", buyer.getState());
					location.put("country", buyer.getCountry());
					location.put("count", 1);
					locations.put(key, location);
				}
			}
		}
		if (score >= 10 && numOfBuyersQualified >= 4) {
			contentItem.put("marketing_qualified", true);
		} else {
			contentItem.put("marketing_qualified", false);
		}
		contentItem.put("score", score);
		contentItem.put("buyer_count", buyers.size());

		Map<String, Object> activityCount = new HashMap<>();
		activityCount.put("ad_clicks", adClicks);
		activityCount.put("website_visits", websiteVisits);
		activityCount.put("form_fills", formFills);
		activityCount.put("live_chats", liveChats);
		activityCount.put("total", adClicks + websiteVisits + formFills + liveChats);
		contentItem.put("activity_count", activityCount);

		// persona count
		List<Map<String, Object>> personaCount = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String, Map<String, Object>> entry : personas.entrySet()) {
			personaCount.add(entry.getValue());
		}
		contentItem.put("persona_count", personaCount);

		// location count
		List<Map<String, Object>> locationCount = new ArrayList<Map<String, Object>>();
		for (Map.Entry<String, Map<String, Object>> entry : locations.entrySet()) {
			locationCount.add(entry.getValue());
		}
		contentItem.put("location_count", locationCount);
	}

	@Cacheable(value = "metrics", key = "#id")
	public Map<String, Object> getContentItem(Account account, String id) {
		Map<String, Object> contentItem = new HashMap<String, Object>();
		calculate(account.getBuyers(), contentItem);
		return contentItem;
	}
}
