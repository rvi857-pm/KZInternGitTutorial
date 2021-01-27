package com.kwanzoo.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.Utility.Metric;
import com.kwanzoo.app.Utility.Parse;
import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.model.Activity;
import com.kwanzoo.app.model.Buyer;

@Component
public class MetricService {
	
	@Autowired
	private Parse parse;

	/**
	 * This method calculates the score of an account based on the buyers and their
	 * activities
	 * 
	 * @param account account for which score is calculated
	 * @return score calculated score
	 */
	public Metric getMetrics(Account account) {

		float accountScore = 0;
		List<Buyer> buyers = account.getBuyers();
		
		Map<String, Integer> personaStore = new HashMap<String, Integer>();
		Map<String, Integer> locationStore = new HashMap<String, Integer>();
		char seperator = '$';

		int count = 0;
		int activityCount = 0;

		for (int i = 0; i < buyers.size(); i++) {

			float buyerScore = 0;
			List<Activity> activities = buyers.get(i).getActivities();

			for (int j = 0; j < activities.size(); j++) {

				if (activities.get(j).getActivityType().equals("Ad Click"))
					buyerScore += 1;
				else if (activities.get(j).getActivityType().equals("Website Visit"))
					buyerScore += 0.1;
				else if (activities.get(j).getActivityType().equals("Form Fill"))
					buyerScore += 3;
				else if (activities.get(j).getActivityType().equals("Live Chat"))
					buyerScore += 3;
				else
					continue;
				count++;
			}

			if (buyers.get(i).getJobLevel().equals("C-Level"))
				buyerScore *= 2;
			else if (buyers.get(i).getJobLevel().equals("Owner,Board Member"))
				buyerScore *= 1.75;
			else if (buyers.get(i).getJobLevel().equals("VP,Director"))
				buyerScore *= 1.5;
			else if (buyers.get(i).getJobLevel().equals("Manager"))
				buyerScore *= 1.25;
			else
				buyerScore *= 1;

			if (buyerScore >= 4)
				count++;

			accountScore += buyerScore;

			StringBuilder personaBuilder = new StringBuilder();
			personaBuilder.append(buyers.get(i).getJobLevel());
			personaBuilder.append(seperator);
			personaBuilder.append(buyers.get(i).getJobFunction());
			String key = personaBuilder.toString();

			personaStore.put(key, personaStore.containsKey(key) ? personaStore.get(key) + 1 : 1);
			
			StringBuilder locationBuilder = new StringBuilder();
			locationBuilder.append(buyers.get(i).getCity());
			locationBuilder.append(seperator);
			locationBuilder.append(buyers.get(i).getState());
			locationBuilder.append(seperator);
			locationBuilder.append(buyers.get(i).getCountry());
			key = locationBuilder.toString();

			locationStore.put(key, locationStore.containsKey(key) ? locationStore.get(key) + 1 : 1);

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
}
