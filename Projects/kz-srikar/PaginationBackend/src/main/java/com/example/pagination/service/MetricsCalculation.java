package com.example.pagination.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.pagination.constants.Constants;
import com.example.pagination.model.Account;
import com.example.pagination.model.Activity;
import com.example.pagination.model.Buyer;

@Service
public class MetricsCalculation {

	private Date convertString(String dateString, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private boolean conditionCheckDate(String start, String end, String activityDateString) {
		Date activityDate = convertString(activityDateString, "yyyy-MM-dd");
		Date startDate = new Date();
		Date endDate = new Date();
		if (start != null) {
			startDate = convertString(start, "MMddyyyy");
		}
		if (end != null) {
			endDate = convertString(end, "MMddyyyy");
		}
		if ((start == null && end == null) || (start == null && activityDate.compareTo(startDate) >= 0)
				|| (end == null && activityDate.compareTo(endDate) <= 0)
				|| (activityDate.compareTo(startDate) >= 0 && activityDate.compareTo(endDate) <= 0)) {
			return true;
		}
		return false;
	}

	private void calculateContent(List<Buyer> buyers, Map<String, Object> contentItem, String start, String end) {
		// score and marketing qualified
		float score = 0;
		int numOfBuyersQualified = 0;

		// activity count
		int adClicks, websiteVisits, formFills, liveChats;
		adClicks = websiteVisits = formFills = liveChats = 0;
		contentItem.put("buyer_count", buyers.size());
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
				if (conditionCheckDate(start, end, activity.getDatetime().replaceAll(" .*", ""))) {
					if (activity.getActivityType().equals("Ad Click")) {
						activityScore += Constants.AD_CLICK_SCORE;
						adClicks += 1;
					} else if (activity.getActivityType().equals("Website Visit")) {
						activityScore += Constants.WEBSITE_VISIT_SCORE;
						websiteVisits += 1;
					} else if (activity.getActivityType().equals("Form Fill")) {
						activityScore += Constants.FORM_FILL_SCORE;
						formFills += 1;
					} else if (activity.getActivityType().equals("Live chat")) {
						activityScore += Constants.LIVE_CHAT_SCORE;
						liveChats += 1;
					}
				}
			}
			if (buyer.getJobLevel().equals("C-Level")) {
				activityScore *= Constants.C_LEVEL;
			} else if (buyer.getJobLevel().equals("Owner,Board Member")) {
				activityScore *= Constants.OWNER_BOARD_MEMBER;
			} else if (buyer.getJobLevel().equals("VP,Director")) {
				activityScore *= Constants.VP_DIRECTOR;
			} else if (buyer.getJobLevel().equals("Manager")) {
				activityScore *= Constants.MANAGER;
			}
			if (activityScore >= 4) {
				numOfBuyersQualified++;
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

	private void calculateBuyerContent(Buyer buyer, Map<String, Object> contentItem, String start, String end) {
		// score and marketing qualified
		float score = 0;

		// activity count
		int adClicks, websiteVisits, formFills, liveChats;
		adClicks = websiteVisits = formFills = liveChats = 0;

		List<Activity> activities = buyer.getActivities();
		for (int j = 0; j < activities.size(); j++) {
			Activity activity = activities.get(j);
			if (conditionCheckDate(start, end, activity.getDatetime().replaceAll(" .*", ""))) {
				if (activity.getActivityType().equals("Ad Click")) {
					score += Constants.AD_CLICK_SCORE;
					adClicks += 1;
				} else if (activity.getActivityType().equals("Website Visit")) {
					score += Constants.WEBSITE_VISIT_SCORE;
					websiteVisits += 1;
				} else if (activity.getActivityType().equals("Form Fill")) {
					score += Constants.FORM_FILL_SCORE;
					formFills += 1;
				} else if (activity.getActivityType().equals("Live chat")) {
					score += Constants.LIVE_CHAT_SCORE;
					liveChats += 1;
				}
			}
		}
		if (buyer.getJobLevel().equals("C-Level")) {
			score *= Constants.C_LEVEL;
		} else if (buyer.getJobLevel().equals("Owner,Board Member")) {
			score *= Constants.OWNER_BOARD_MEMBER;
		} else if (buyer.getJobLevel().equals("VP,Director")) {
			score *= Constants.VP_DIRECTOR;
		} else if (buyer.getJobLevel().equals("Manager")) {
			score *= Constants.MANAGER;
		}
		if (score >= 4) {
			contentItem.put("marketing_qualified", true);
		} else {
			contentItem.put("marketing_qualified", false);
		}
		contentItem.put("score", score);

		Map<String, Object> activityCount = new HashMap<>();
		activityCount.put("ad_clicks", adClicks);
		activityCount.put("website_visits", websiteVisits);
		activityCount.put("form_fills", formFills);
		activityCount.put("live_chats", liveChats);
		activityCount.put("total", adClicks + websiteVisits + formFills + liveChats);
		contentItem.put("activity_count", activityCount);

	}

	@Cacheable(value = "accountcMetrics", key = "{#id, #start, #end}")
	public Map<String, Object> getContentItem(Account account, String id, String start, String end) {
		Map<String, Object> contentItem = new HashMap<String, Object>();
		calculateContent(account.getBuyers(), contentItem, start, end);
		return contentItem;
	}

	@Cacheable(value = "buyerMetrics", key = "{#id, #start, #end}")
	public Map<String, Object> getBuyerContentItem(Buyer buyer, String id, String start, String end) {
		Map<String, Object> contentItem = new HashMap<String, Object>();
		calculateBuyerContent(buyer, contentItem, start, end);
		return contentItem;
	}

}
