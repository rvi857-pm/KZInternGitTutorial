package com.example.demo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Activity;
import com.example.demo.model.Buyer;

@Service
public class CalaculationMetrics implements Serializable{

/**
 * Global variable accecble to all methods in class
 */
int adClick = 0, webVisit = 0, formFill = 0, liveChat = 0, totalActivity = 0, qualified  = 0;

/**
 * 
 * @param type activity type
 * @return activity score
 */
private double getActivityScore(String type) {
	
	totalActivity++;
	double ans = 0;
	switch(type) {
	
	case("Ad Click"):
		ans = 1.0;
		adClick++;
		break;
	case ("Form Fill"):
		ans = 3.0;
		formFill ++;
		break;
	case ( "Live Chat"):
		ans = 3.0;
		liveChat++;
		break;
	case ( "Website Visit"):
		ans = 0.1;
		webVisit++;
		break;
	default:
		ans = 0.0;
		break;
	}
	
	return ans;
}

/**
 * 
 * @param jobLevel job_lvel of buyer
 * @return  job level point
 */
private double getBuyerValue ( String jobLevel) {
	double ans = 0;
	
	switch( jobLevel) {
	case ("C-Level"):
		ans = 2.0;
		break;
	case ("VP,Director"):
		ans = 1.5;
		break;
	case ( "Owner/Board Member"):
		ans = 1.75;
		break;
	case ( "Manager"):
		ans = 1.25;
		break;
	default:
		ans = 1.0;
		break;
	}
	return ans;
}
/**
 * 
 * @param string check the string is null
 * @return
 */
private String check( String string) {
	
	if ( string == null)
		return "";
	return string;
}

/**
 * 
 * @param buyer
 * @param buyer_id
 * @param start
 * @param end
 * @return activity score for each buyer
 */
//@Cacheable(value = "staticMetrics", key = "{#buyer_id, #start, #end}")
private double calculate( Buyer buyer,String buyer_id,String start, String end) {
	

	List<Activity> activities = buyer.getActivities();
	
	double totalActivityScore = 0.0;
	for ( int itr = 0; itr < activities.size(); itr++) {
		
		Activity activity  = activities.get(itr);
		totalActivityScore += getActivityScore( activity.getActivityType());
	}
	
	return totalActivityScore;
}

/**
 * Calculate metrics
 * @param account
 * @param id
 * @param start
 * @param end
 * @return
 */

@Cacheable(value = "staticMetrics", key = "{#id}")
public Map<String, Object>  getComputed(Account account, String id, String start, String end) {
		
	
	Map<String, Object> mapAccount = new HashMap<String , Object>();
	adClick = 0; webVisit = 0; formFill = 0; liveChat = 0; totalActivity = 0; qualified  = 0;
	float totalBuyerScore = 0;
	Map<String, Map<String, Object>> locations = new HashMap<>();
	Map<String, Map<String, Object>> persons = new HashMap<>();
	List<Map<String,Object>> locationCount = new ArrayList<Map<String, Object>>();
	List<Map<String,Object>> personCount = new ArrayList<Map<String, Object>>();
	
	
	List<Buyer> buyers = account.getBuyers();
	int buyerSize = buyers.size();
	mapAccount.put("buyers_count", buyerSize);
	
	for ( int itr = 0; itr < buyerSize; itr++) {
		
		Buyer buyer = buyers.get(itr);
		String buyerId = buyer.getId();
		//////////////////////////////////////////////////////////////////////////////////////////////////
		double score = ( calculate( buyer, buyerId,start, end) * getBuyerValue(buyer.getJobLevel()));
		if ( score >= 4)
			qualified++;
		totalBuyerScore += score; 
		////////////////////////////////////////////////////////////////////////////////////////////////
		String city = buyer.getCity(), state = buyer.getState(), country = buyer.getCountry();
		
		String locationKey = check( city) + check( state) + check(country);
		if ( locationKey != "") {
			if ( locations.containsKey(locationKey)) {
				Map<String, Object> temp = locations.get(locationKey);
				temp.replace("count",( (int) temp.get("count")) + 1);
			}
			else {
				Map<String, Object> temp = new HashMap<>(){
					{
						put("city", city);
						put("state", state);
						put("country", country);
						put("count", 1);
					}
				};
				locations.put(locationKey, temp); 
				locationCount.add(temp);
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		String personKey = check( buyer.getJobFunction()) + check( buyer.getJobLevel());
		if ( personKey != "") {
			if ( persons.containsKey(personKey)) {
				Map<String, Object> temp = persons.get(personKey);
				temp.replace("count",( (int) temp.get("count")) + 1);
			}
			else {
				Map<String, Object> temp =new HashMap<>(){
					{
						put("job_level" , buyer.getJobLevel());
						put( "job_function", buyer.getJobFunction());
						put( "count", 1);
					}
				}; 
				persons.put(personKey, temp); 
				personCount.add(temp);
			}
		}
		
	}
	
		////////////////////////////////////////////////////////////////////////////////////////////////
	Map<String, Object> temp = new HashMap<>(){
		{
			put("ad_clicks", adClick);
			put("website_visits", webVisit);
			put("form_fills", formFill);
			put("live_charts", liveChat);
			put("total", totalActivity);
		}
	};
	
	boolean marketing_qualified = false;
	if ( qualified >= 3 && totalBuyerScore >= 10)
		marketing_qualified = true;
	
	mapAccount.put("score", totalBuyerScore);
	mapAccount.put("activity_ount",temp);
	mapAccount.put("persona_count", personCount);
	mapAccount.put("location_count", locationCount);
	mapAccount.put("marketing_qualified", marketing_qualified);
	return mapAccount;
}
}