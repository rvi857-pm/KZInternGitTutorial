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
	private void calculate(List<Buyer> buyers, Map<String, Object> contentItem, String metricType) {
		if (metricType.equals("score")) {
			float score = 0;
			float numOfBuyersQualified = 0;
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
			}
			if (score >= 10 && numOfBuyersQualified >= 4) {
				contentItem.put("marketing_qualified", true);
			} else {
				contentItem.put("marketing_qualified", false);
			}
			contentItem.put("score", score);

		} else if (metricType.equals("marketing_qualified") && !contentItem.containsKey("marketing_qualified")) {
			calculate(buyers, contentItem, "score");

		} else if (metricType.equals("buyer_count")) {
			contentItem.put("buyer_count", buyers.size());

		} else if (metricType.equals("activity_count")) {

			int adClicks, websiteVisits, formFills, liveChats;
			adClicks = websiteVisits = formFills = liveChats = 0;

			for (int i = 0; i < buyers.size(); i++) {
				List<Activity> activities = buyers.get(i).getActivities();
				for (int j = 0; j < activities.size(); j++) {
					Activity activity = activities.get(j);
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
			}

			Map<String, Object> activityCount = new HashMap<>();
			activityCount.put("ad_clicks", adClicks);
			activityCount.put("website_visits", websiteVisits);
			activityCount.put("form_fills", formFills);
			activityCount.put("live_chats", liveChats);
			activityCount.put("total", adClicks + websiteVisits + formFills + liveChats);

			contentItem.put("activity_count", activityCount);

		} else if (metricType.equals("persona_count")) {

			Map<String, Map<String, Object>> personas = new HashMap<>();
			for (int i = 0; i < buyers.size(); i++) {
				Buyer buyer = buyers.get(i);
				if (buyer.getJobLevel().equals("") && buyer.getJobFunction().equals("")) {
					continue;
				}
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
			List<Map<String, Object>> personaCount = new ArrayList<Map<String, Object>>();

			for (Map.Entry<String, Map<String, Object>> entry : personas.entrySet()) {
				personaCount.add(entry.getValue());
			}
			contentItem.put("persona_count", personaCount);

		} else if (metricType.equals("location_count")) {
			Map<String, Map<String, Object>> locations = new HashMap<>();
			for (int i = 0; i < buyers.size(); i++) {
				Buyer buyer = buyers.get(i);
				if (buyer.getCity().equals("") && buyer.getState().equals("") && buyer.getCountry().equals("")) {
					continue;
				}
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
			List<Map<String, Object>> locationCount = new ArrayList<Map<String, Object>>();

			for (Map.Entry<String, Map<String, Object>> entry : locations.entrySet()) {
				locationCount.add(entry.getValue());
			}
			contentItem.put("location_count", locationCount);

		} else if (metricType.equals("all")) {
			calculate(buyers, contentItem, "score");
			calculate(buyers, contentItem, "buyer_count");
			calculate(buyers, contentItem, "activity_count");
			calculate(buyers, contentItem, "persona_count");
			calculate(buyers, contentItem, "location_count");
		}

	}

	@Cacheable(value = "metrics", key = "#id")
	public Map<String, Object> getContentItem(Account account, String id) {
		Map<String, Object> contentItem = new HashMap<String, Object>();
		calculate(account.getBuyers(), contentItem, "all");
		return contentItem;
	}
}
