package com.example.demo.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

private Date getDate( String dateString ) {

	long dateS = Long.parseLong(dateString);
	Date date;
	try {
		date = new Date(dateS);
		return date;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
private Date convert( String dateString) {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
	Date date = new Date();
	try {
		date = dateFormat.parse(dateString);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return date;
}
private boolean checkDate( String start, String end , String date) {
	
	if( start == null && end == null)
		return true;
	
	
	Date startDate, endDate, actDate;
	
	actDate = getDate(date);
	if( start != null) 
		startDate = convert(start);
	else
		startDate = actDate;
	if( end != null)
		endDate = convert(end);
	else
		endDate = actDate;
	
	if( actDate.compareTo(startDate) >=0 && actDate.compareTo(endDate) <= 0) 
		return true;
	
	return false;
		
}
/**
 * 
 * @param buyer
 * @param buyer_id
 * @param start
 * @param end
 * @return activity score for each buyer
 */
private double calculate( Buyer buyer,String buyer_id,String start, String end) {
	

	List<Activity> activities = buyer.getActivities();
	
	double totalActivityScore = 0.0;
	for ( int itr = 0; itr < activities.size(); itr++) {
		
		
		Activity activity  = activities.get(itr);
		//String date = activity.getDatetime();
		
		if ( checkDate(start,end,activity.getDatetime()) )
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

public Map<String, Object> compute(List<Buyer> buyers,String start, String end) {
		
	
	Map<String, Object> mapAccount = new HashMap<String , Object>();
	adClick = 0; webVisit = 0; formFill = 0; liveChat = 0; totalActivity = 0; qualified  = 0;
	float totalBuyerScore = 0;
	Map<String, Map<String, Object>> locations = new HashMap<>();
	Map<String, Map<String, Object>> persons = new HashMap<>();
	List<Map<String,Object>> locationCount = new ArrayList<Map<String, Object>>();
	List<Map<String,Object>> personCount = new ArrayList<Map<String, Object>>();
	
	
	
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
	Map<String, Object> activityCount = new HashMap<>(){
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
	mapAccount.put("activity_count",activityCount);
	mapAccount.put("persona_count", personCount);
	mapAccount.put("location_count", locationCount);
	mapAccount.put("marketing_qualified", marketing_qualified);
	return mapAccount;
}

@Cacheable(value = "staticMetrics", key = "{#id, #start, #end}")
public Map<String, Object> getComputed(Account account , String id, String start, String end){
		
		List<Buyer> buyers = account.getBuyers();
		return compute( buyers,start, end);
	}
}