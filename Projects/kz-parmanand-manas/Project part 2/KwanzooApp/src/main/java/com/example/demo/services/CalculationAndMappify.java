package com.example.demo.services;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.controller.helper_controller;
import com.example.demo.model.Account;
import com.example.demo.model.Activity;
import com.example.demo.model.Buyer;
import com.example.demo.model.ResponseList;

@SuppressWarnings("serial")
@Service
public class CalculationAndMappify implements Serializable{

  @Autowired
  helper_controller t;
  
  public ResponseList calAndMappify_Buyer(List<Buyer> transit, List<String> metrics, List<String> exclude, String start, String end) throws ParseException{
	  ResponseList response = new ResponseList();
		response.setTotal_count(transit.size());
		List<LinkedHashMap<String, Object>> e_list = new ArrayList<LinkedHashMap<String, Object>>();
		
		for(int i=0;i<transit.size();i++) {
			LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
			Buyer buyer = transit.get(i);
			entity.put("id", buyer.getId());
			entity.put("account_id", buyer.getAccount_id());
			entity.put("account_name", buyer.getAccount().getName());
			entity.put("job_level", buyer.getJob_level());
			entity.put("job_function", buyer.getJob_function());
			entity.put("city", buyer.getCity());
			entity.put("state", buyer.getState());
			entity.put("country", buyer.getCountry());
			entity.put("source", buyer.getSource());
			
			for(int j=0;j<metrics.size(); j++) {
				if(metrics.get(j).equals("all")) {
					String ckb_s = buyer.getId() + start + end;
					String ckb_mq = buyer.getId() + start + end;
					entity.put("score", t.getScore_buyer(buyer, ckb_s, start, end));
					entity.put("activity_count", buyer.getActivity_list().size());
					entity.put("marketing_qualified", t.getMarketing_qualification_Buyer(buyer, ckb_mq, start, end));
				}
				else {
					String ckb_s = buyer.getId() + start + end;
					if(metrics.get(j).equals("score")) {
						entity.put("score", t.getScore_buyer(buyer, ckb_s, start, end));
					}
					else if (metrics.get(j).equals("activity_count")) {
						entity.put("activity_count", buyer.getActivity_list().size());
					}
					else if(metrics.get(j).equals("marketing_qualified")) {
						String ckb_mq = buyer.getId() + start + end;
						entity.put("marketing_qualification", t.getMarketing_qualification_Buyer(buyer, ckb_mq, start, end));
					 }
				}
			}
			
			for(int p=0;p< exclude.size() ;p++) {
				if(entity.containsKey(exclude.get(p))) {
					entity.remove(exclude.get(p));
				}
		      }
			e_list.add(entity);
		}
		response.setEntity_list(e_list);
		return response;
  }
  	
  public ResponseList calAndMappify(List<Account> transit, List<String> metrics, List<String> exclude, String start, String end) throws ParseException {
		ResponseList response = new ResponseList();
		response.setTotal_count(transit.size());
		List<LinkedHashMap<String, Object>> e_list = new ArrayList<LinkedHashMap<String, Object>>();
		
		for(int i=0; i< transit.size() ; i++){
			LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
			entity  = t.setAll(transit.get(i), transit.get(i).getId());
			    for(int j=0; j<metrics.size() ; j++) {
			    	
			    	if(metrics.get(j).equals("all")) {
			    		String ck_mkt_qualify = transit.get(i).getId() + start + end;
			    		String ck_activity_count = transit.get(i).getId() + start + end;
			    		String ck_score = transit.get(i).getId() + start + end;
			    		entity.put("score", t.getScore(transit.get(i), ck_score, start, end));
			    		entity.put("buyer_count", t.getBuyer_count(transit.get(i), transit.get(i).getId()));
			    		entity.put("marketing_qualified", t.getMarketing_qualification(transit.get(i),ck_mkt_qualify, start, end));
			    		entity.put("activity_count", t.getActivityCount(transit.get(i),ck_activity_count, start, end));
			    		entity.put("persona_count", t.getPersona_Count(transit.get(i),transit.get(i).getId()));
			    		entity.put("location_count", t.getLocation_Count(transit.get(i),transit.get(i).getId()));
			    	} else {
			    		
				    	if(metrics.get(j).equals("score")) {
				    		String ck_score = transit.get(i).getId() + start + end;
				    		entity.put("score", t.getScore(transit.get(i), ck_score, start, end));
				    	}
				    	if(metrics.get(j).equals("buyer_count")) {
				    		entity.put("buyer_count", t.getBuyer_count(transit.get(i), transit.get(i).getId()));
				    	}
				    	if(metrics.get(j).equals("marketing_qualified")) {
				    		String ck_mkt_qualify = transit.get(i).getId() + start + end;
				    		entity.put("marketing_qualified", t.getMarketing_qualification(transit.get(i),ck_mkt_qualify,start, end));
				    	}
				    	if(metrics.get(j).equals("activity_count")) {
				    		String ck_activity_count = transit.get(i).getId() + start + end;
				    		entity.put("activity_count", t.getActivityCount(transit.get(i), ck_activity_count, start, end));
				    	}
				    	if(metrics.get(j).equals("persona_count")) {
				    		entity.put("persona_count", t.getPersona_Count(transit.get(i), transit.get(i).getId() ));
				    	}
				    	if(metrics.get(j).equals("location_count")) {
				    		entity.put("location_count", t.getLocation_Count(transit.get(i), transit.get(i).getId()));
				    	}
			    	
			    	}
			    }
			    
			for(int p=0;p< exclude.size() ;p++) {
				if(entity.containsKey(exclude.get(p))) {
					entity.remove(exclude.get(p));
				}
			}
			
			e_list.add(entity);
		}
		response.setEntity_list(e_list);
		return response;
	}
  
  public ResponseList calAndMappify_Activity(List<Activity> transit) {
	  ResponseList response = new ResponseList();
		response.setTotal_count(transit.size());
		List<LinkedHashMap<String, Object>> e_list = new ArrayList<LinkedHashMap<String, Object>>();
		
		for(int i=0; i< transit.size() ; i++){
			LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
			Activity activity = transit.get(i);
			entity.put("id", activity.getId());
			entity.put("buyer_id", activity.getBuyer_id());
			entity.put("datatime",activity.getDatetime());
			entity.put("activity_type", activity.getActivity_type());
			entity.put("details", activity.getDetails());
					
	  e_list.add(entity);
  }
	  response.setEntity_list(e_list);
		return response;
  }
  
  
}
