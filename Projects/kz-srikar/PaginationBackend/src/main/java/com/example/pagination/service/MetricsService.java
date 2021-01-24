package com.example.pagination.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import com.example.pagination.model.Account;
import com.example.pagination.model.Activity;
import com.example.pagination.model.Buyer;
import com.example.pagination.model.PageResponse;

@Service
public class MetricsService {

	private void calculate(List<Buyer> buyers, Map<String, Object> contentItem, String metricType) {
		if (metricType == "score") {
			float score = 0;
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

				score += activityScore;
			}
			contentItem.put("score", score);

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

		} else if (metricType.equals("location_count")) {

		} else if (metricType.equals("all")) {
			calculate(buyers, contentItem, "score");
			calculate(buyers, contentItem, "buyer_count");
			calculate(buyers, contentItem, "activity_count");
			calculate(buyers, contentItem, "persona_count");
			calculate(buyers, contentItem, "location_count");
		}

	}

	public PageResponse metricsServiceUtility(Page<Account> accountPage, String metrics) {

		String[] metricParams = new String[0];
		if (metrics != null) {
			metricParams = metrics.split("[,]", 0);
		}

		List<Account> accounts = accountPage.getContent();

		PageResponse response = new PageResponse();
		response.setTotalElements((int) accountPage.getTotalElements());

		List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < accounts.size(); i++) {

			Map<String, Object> contentItem = new HashMap<String, Object>();

			Account account = accounts.get(i);

			contentItem.put("id", account.getId());
			contentItem.put("name", account.getName());
			contentItem.put("ip_domain", account.getIpDomain());
			contentItem.put("city", account.getCity());
			contentItem.put("state", account.getState());
			contentItem.put("country", account.getCountry());
			contentItem.put("type", account.getType());
			contentItem.put("salesforce_id", account.getSalesforceId());

			for (int j = 0; j < metricParams.length; j++) {

				calculate(account.getBuyers(), contentItem, metricParams[j]);
			}
			content.add(contentItem);
		}

		response.setContent(content);

		return response;
	}
}
