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

import javafx.util.Pair;

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
		
		Map<Pair<String, String>, Integer> personaStore = new HashMap<Pair<String, String>, Integer>();
		Map<Pair<String, Pair<String, String>>, Integer> locationStore = new HashMap<Pair<String, Pair<String, String>>, Integer>();

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
			
			Pair<String, String> newPair = new Pair<String, String>(buyers.get(i).getJobLevel(), buyers.get(i).getJobFunction());
			personaStore.put(newPair, personaStore.containsKey(newPair) ? personaStore.get(newPair) + 1 : 1);
			
			Pair<String, String> tempPair = new Pair<String, String>(buyers.get(i).getState(), buyers.get(i).getCountry());
			Pair<String, Pair<String, String>> locPair = new Pair<String, Pair<String, String>>(buyers.get(i).getCity(), tempPair);
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
}
