package com.example.kz_assign.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.kz_assign.models.account;
import com.example.kz_assign.models.activity;
import com.example.kz_assign.models.buyer;

@Service
public class MetricsService {
	
	private static Map<String, Integer> multipliermapping;
	
	static {
		multipliermapping = new HashMap<>();
		multipliermapping.put("c-level", 200);
		multipliermapping.put("owner", 175);
		multipliermapping.put("board member", 175);
		multipliermapping.put("vp", 150);
		multipliermapping.put("director", 150);
		multipliermapping.put("manager", 200);
		multipliermapping.put("c-level", 200);
	}
	
	private static Map<String, Integer> activitymapping;
	
	static {
		activitymapping = new HashMap<>();
		activitymapping.put("Ad Click", 10);
		activitymapping.put("Website Visit", 1);
		activitymapping.put("Form Fill", 40);
		activitymapping.put("Live Chat", 30);
	}
		
 	private Map<String, Object> toMap(account acc) {
	    Map<String, Object> result = new LinkedHashMap<String, Object>();
	    result.put("id", acc.getId().toString());
	    result.put("name", acc.getName());
	    result.put("ip_domain", acc.getIp_domain());
	    result.put("city", acc.getCity());
	    result.put("state", acc.getState());
	    result.put("country", acc.getCountry());
	    result.put("type", acc.getType());
	    result.put("salesforce_id", acc.getSalesforce_id());
	    return result;
	}
	
	List<Map<String,Object>> tomaplist(List<account> accountslist){
		 List<Map<String, Object>> datamap = new ArrayList<Map<String, Object>>();
		 
		 for(int i=0; i<accountslist.size(); i++) {
			 account acc = accountslist.get(i);
			 Map<String, Object> datamapsample = toMap(acc);
			 datamap.add(datamapsample);
		 }
		 
		 return datamap;
	}

	
	private void addscorefield(account acc, Map<String,Object> datamapsample){
		int accountscore = 0;
		List<buyer> buyerslist = new ArrayList<buyer>(acc.getBuyers()); 
		
		for(int i=0; i<buyerslist.size(); i++) {
			int multiplier = multipliermapping.getOrDefault(buyerslist.get(i).getCity(), 100);
			List<activity> activitylist = new ArrayList<>(buyerslist.get(i).getActivities());
			for(int j=0; j<activitylist.size(); j++) {
				accountscore += (activitymapping.getOrDefault(activitylist.get(j).getType(), 0))*multiplier;
			}
		}
		datamapsample.put("score", ((float)accountscore/1000));
		

	}

	private void addbuyer_countfield(account acc, Map<String,Object>datamapsample) {
		datamapsample.put("buyer_count", acc.getBuyers().size());
	}
	
	private void addactivity_countfield(account acc, Map<String,Object>datamapsample) {
		List<buyer> buyerslist = new ArrayList<buyer>(acc.getBuyers()); 
		Map<String,Integer> buyeractivities = new LinkedHashMap<>();
		buyeractivities.put("Ad Click", 0);
		buyeractivities.put("Website Visit", 0);
		buyeractivities.put("Form Fill", 0);
		buyeractivities.put("Live Chat", 0);
		for(int i=0; i<buyerslist.size(); i++) {
			List<activity> activitylist = new ArrayList<>(buyerslist.get(i).getActivities());
			
			for(int j=0; j<activitylist.size(); j++) {
				int temp = buyeractivities.get(activitylist.get(j).getType())+1;
				 buyeractivities.replace(activitylist.get(j).getType(), temp);
			}
		}
		datamapsample.put("activity_count",buyeractivities);
	}
	
	public void addpersona_countfield(account acc, Map<String,Object>datamapsample) {
		List<buyer> buyerslist = new ArrayList<buyer>(acc.getBuyers());
		List<Map<String,Object>> persona_countlist = new ArrayList<>();
		Map<String,Map<String,Object>> buyerlocations = new LinkedHashMap<>();
		for	(buyer buy:buyerslist) {
			String city = buy.getCity();
			String state = buy.getState();
			String buyerlocationkey = city + state;
			if(buyerlocations.get(buyerlocationkey) != null) {
				Map<String, Object> bl = (Map<String, Object>) buyerlocations.get(buyerlocationkey);
				int count = (int) bl.get("count");
				count = count +1 ;
				bl.replace("count", count);
				buyerlocations.replace(buyerlocationkey, bl);
			}
			else {
				Map<String, Object> bl = new LinkedHashMap<>();
				bl.put("city", city);
				bl.put("state", state);
				bl.put("count", 1);
				buyerlocations.put(buyerlocationkey, bl);
			}
		}
		for (Map<String,Object>bl : buyerlocations.values())  
            persona_countlist.add(bl);
		datamapsample.put("Persona_count", persona_countlist);
	}
	
	public void addlocation_countfield(account acc, Map<String,Object>datamapsample) {
		List<buyer> buyerslist = new ArrayList<buyer>(acc.getBuyers());
		List<Map<String,Object>> location_countlist = new ArrayList<>();
		Map<String,Map<String,Object>> buyerlocations = new LinkedHashMap<>();
		for	(buyer buy:buyerslist) {
			String city = buy.getCity();
			String state = buy.getState();
			String country = buy.getCountry();
			String buyerlocationkey = city + state + country;
			if(buyerlocations.get(buyerlocationkey) != null) {
				Map<String, Object> bl = (Map<String, Object>) buyerlocations.get(buyerlocationkey);
				int count = (int) bl.get("count");
				count = count +1 ;
				bl.replace("count", count);
				buyerlocations.replace(buyerlocationkey, bl);
			}
			else {
				Map<String, Object> bl = new LinkedHashMap<>();
				bl.put("city", city);
				bl.put("state", state);
				bl.put("country", country);
				bl.put("count", 1);
				buyerlocations.put(buyerlocationkey, bl);
			}
		}
		for (Map<String,Object>bl : buyerlocations.values())  
            location_countlist.add(bl);
		datamapsample.put("Location_count", location_countlist);
	}
	
	public List<Map<String,Object>> addmetric(List<account> accountslist, String metric, List<Map<String,Object>> datamaplist ){
//		System.out.println(metric);
		if(metric.equals("score")) {
//			System.out.println("Check for metrics");
			for(int i=0; i<accountslist.size(); i++) {
				addscorefield(accountslist.get(i), datamaplist.get(i));
			}
		}
		if(metric.equals("buyer_count")) {
			for(int i=0; i<accountslist.size(); i++) {
				addbuyer_countfield(accountslist.get(i), datamaplist.get(i));
			}
		}
		if(metric.equals("activity_count")) {
			for(int i=0; i<accountslist.size(); i++) {
				addactivity_countfield(accountslist.get(i), datamaplist.get(i));
			}
		}
		if(metric.equals("persona_count")) {
			for(int i=0; i<accountslist.size(); i++) {
				addpersona_countfield(accountslist.get(i), datamaplist.get(i));
			}
		}
		if(metric.equals("location_count")) {
			for(int i=0; i<accountslist.size(); i++) {
				addlocation_countfield(accountslist.get(i), datamaplist.get(i));
			}
		}
		return datamaplist;
	}
	
	public  List<Map<String,Object>> addmetrics(List<account> accountslist, List<String> metrics){
		if(metrics.get(0).equals("all")) {
			metrics.remove(0);
			metrics.add("score");
			metrics.add("buyer_count");
			metrics.add("activity_count");
			metrics.add("persona_count");
			metrics.add("location_count");
		}
		System.out.println("metrics check:" + metrics);
		List<Map<String,Object>> datamaplist = tomaplist(accountslist);
		for(String metric:metrics) {
			datamaplist = addmetric(accountslist, metric,datamaplist);
		}
		
		return datamaplist;
	}
	
}
	

