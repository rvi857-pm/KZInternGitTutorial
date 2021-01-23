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
	
	private static Map<String, Double> multipliermapping;
	
	static {
		multipliermapping = new HashMap<>();
		multipliermapping.put("c-level", 2.0);
		multipliermapping.put("owner", 1.750);
		multipliermapping.put("board member", 1.750);
		multipliermapping.put("vp", 1.50);
		multipliermapping.put("director", 1.50);
		multipliermapping.put("manager", 2.0);
		multipliermapping.put("c-level", 2.0);
	}
	
	private static Map<String, Double> activitymapping;
	
	static {
		activitymapping = new HashMap<>();
		activitymapping.put("Ad Click", 1.0);
		activitymapping.put("Website Visit", 0.10);
		activitymapping.put("Form Fill", 4.0);
		activitymapping.put("Live Chat", 3.0);
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
		double accountscore = 0.0;
		List<buyer> buyerslist = new ArrayList<buyer>(acc.getBuyers()); 
		
		for(int i=0; i<buyerslist.size(); i++) {
			double multiplier = multipliermapping.getOrDefault(buyerslist.get(i).getJob_level(), 1.0);
			List<activity> activitylist = new ArrayList<>(buyerslist.get(i).getActivities());
			for(int j=0; j<activitylist.size(); j++) {
				accountscore += (activitymapping.getOrDefault(activitylist.get(j).getType(), 0.0))*multiplier;
			}
		}
		datamapsample.put("score", accountscore);
		

	}

	public List<Map<String,Object>> addmetrics(List<account> accountslist, String metric){
		List<Map<String,Object>> datamaplist = tomaplist(accountslist);
		System.out.println(metric);
		if(metric.equals("score")) {
			System.out.println("Check for metrics");
			for(int i=0; i<accountslist.size(); i++) {
				addscorefield(accountslist.get(i), datamaplist.get(i));
			}
		}
		return datamaplist;
	}
}
	

