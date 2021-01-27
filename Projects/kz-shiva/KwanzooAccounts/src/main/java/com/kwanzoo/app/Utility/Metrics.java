package com.kwanzoo.app.Utility;

import java.util.List;

import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.model.Activity;
import com.kwanzoo.app.model.Buyer;

@Component
public class Metrics {

	/**
	 * This method calculates the score of an account based on the buyers and their
	 * activities
	 * 
	 * @param account account for which score is calculated
	 * @return score calculated score
	 */
	public Metric getScore(Account account) {

		float accountScore = 0;
		List<Buyer> buyers = account.getBuyers();

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

		}
		Metric metric = new Metric();
		boolean flag = count >= 3 ? true : false;
		metric.setScore(accountScore);
		metric.setQualified(flag);
		metric.setBuyerCount(buyers.size());
		metric.setActivityCount(activityCount);
		return metric;
	}
}
