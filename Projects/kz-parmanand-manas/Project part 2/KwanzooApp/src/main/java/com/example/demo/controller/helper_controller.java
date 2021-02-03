package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.model.Activity;
import com.example.demo.model.Buyer;

@RestController
public class helper_controller {
   
	
	@Cacheable(key = "#id", value = "buyer_count")
	  public long getBuyer_count(Account account, String id) {
		  System.out.println(" getBuyer_count called");
		  return account.getBuyer_list().size();
	  }
	
	
	
	@Cacheable(key = "#id", value="entity")
	public LinkedHashMap<String, Object> setAll(Account account, String id){
		 LinkedHashMap<String, Object> entity = new LinkedHashMap<String, Object>();
		    entity.put("id", id);
			entity.put("name", account.getName());
			entity.put("ip_domain", account.getIp_domain());
			entity.put("city", account.getCity());
			entity.put("state", account.getState());
			entity.put("country", account.getCountry());
			entity.put("type", account.getType());
			entity.put("salesforce_id", account.getSalesforce_id());
			return entity;
	 }
	
	@Cacheable(key = "#cachekey", value="get_score_b")
	public float getScore_buyer(Buyer buyer, String cachekey, String start, String end)throws ParseException{
		float b_score = 0;
		for(int i=0;i<buyer.getActivity_list().size() ; i++) {
			Activity activity = buyer.getActivity_list().get(i);
			
			if(isValidDateToConsider(activity.getDatetime(), start, end)) {
				  if(activity.getActivity_type().equals("Website Visit")) {
					  b_score += 0.1;
				  }
				  else if(activity.getActivity_type().equals("Ad Click")) {
					   b_score += 1;  
				  }
				  else if(activity.getActivity_type().equals("Form Fill")) {
					  b_score += 3;
				  }
				  else if(activity.getActivity_type().equals("Live Chat")) {
					  b_score += 3;
				  }
			  }
		}
		return b_score;
		
	}
	
	@Cacheable(key = "#cachekey", value="get_score")  
	public float getScore(Account account, String cachekey, String start, String end) throws ParseException {
		System.out.println(" getScore called");  
		float score = 0; 
		  for(int i=0;i<account.getBuyer_list().size();i++) {
			  Buyer buyer = account.getBuyer_list().get(i);
			  float temp_score = 0;
			  for(int j=0;j<buyer.getActivity_list().size();j++) {
				  Activity activity = buyer.getActivity_list().get(j);
				  
				  if(isValidDateToConsider(activity.getDatetime(), start, end)) {
					  if(activity.getActivity_type().equals("Website Visit")) {
						  temp_score += 0.1;
					  }
					  else if(activity.getActivity_type().equals("Ad Click")) {
						   temp_score += 1;  
					  }
					  else if(activity.getActivity_type().equals("Form Fill")) {
						  temp_score += 3;
					  }
					  else if(activity.getActivity_type().equals("Live Chat")) {
						  temp_score += 3;
					  }
				  }
			  }
			  if(buyer.getJob_level().equals("C-Level")){
				  temp_score*= 2;
			  }
			  else if(buyer.getJob_level().equals("Owner/Board Member")) {
				  temp_score *= 1.75;
			  }
			  else if(buyer.getJob_level().equals("VP,Director")) {
				  temp_score*= 1.5;
			  }
			  else if(buyer.getJob_level().equals("Manager")){
				  temp_score*= 1.25;
			  }
			  score += temp_score;
		  }
		  return score;
	  }
	
	
	@Cacheable(key="#cachekey", value="market_qualify_b")
	public boolean getMarketing_qualification_Buyer(Buyer buyer,String cachekey, String start, String end) throws ParseException {
        
		if(getScore_buyer(buyer, cachekey, start, end) >=4) {
			return true;
		}
		else{
			return false;
		}
	
	}
	
	
	@Cacheable(key="#cachekey", value="market_qualify")
	public boolean getMarketing_qualification(Account account, String cachekey, String start, String end) throws ParseException {
		System.out.println(" getMarketing Qualification  called");  
		float score = 0; 
		  long qualified_buyers_count=0;
		  for(int i=0;i<account.getBuyer_list().size();i++) {
			  Buyer buyer = account.getBuyer_list().get(i);
			  float temp_score = 0;
			  for(int j=0;j<buyer.getActivity_list().size();j++) {
				  Activity activity = buyer.getActivity_list().get(j);
				  
				  if(isValidDateToConsider(activity.getDatetime(), start, end)) {
					  if(activity.getActivity_type().equals("Website Visit")) {
						  temp_score += 0.1;
					  }
					  else if(activity.getActivity_type().equals("Ad Click")) {
						   temp_score += 1;  
					  }
					  else if(activity.getActivity_type().equals("Form Fill")) {
						  temp_score += 3;
					  }
					  else if(activity.getActivity_type().equals("Live Chat")) {
						  temp_score += 3;
					  }
				  }
			  }
			  if(buyer.getJob_level().equals("C-Level")){
				  temp_score*= 2;
			  }
			  else if(buyer.getJob_level().equals("Owner/Board Member")) {
				  temp_score *= 1.75;
			  }
			  else if(buyer.getJob_level().equals("VP,Director")) {
				  temp_score*= 1.5;
			  }
			  else if(buyer.getJob_level().equals("Manager")){
				  temp_score*= 1.25;
			  }
			  if(temp_score >= 4) {
				  qualified_buyers_count++;
			  }
			  score += temp_score;
		  }
		  if(score >= 10 && qualified_buyers_count >=3 ) {
			  return true;
		  }
		  return false;
	  }
	  	  
	  
	  @Cacheable(key="#id", value ="persona_count")
	  public List<LinkedHashMap<String,Object>> getPersona_Count(Account account, String id){
		  System.out.println(" getPersona_count called");
		  Map<String, LinkedHashMap<String,Object>> helper = new LinkedHashMap<String, LinkedHashMap<String,Object>>();
	      for(int i=0; i<account.getBuyer_list().size(); i++) {
	    	 Buyer buyer = account.getBuyer_list().get(i);
	    	 if(!(buyer.getJob_function().equals("") || buyer.getJob_level().equals(""))) {
	    		 String matcher = buyer.getJob_function() + buyer.getJob_level();
	    		 if(helper.containsKey(matcher)) {
	    			 	LinkedHashMap<String, Object> persona_entity = helper.get(matcher);
	    			 	persona_entity.replace("count", ((int)persona_entity.get("count") + 1));
	    			 	helper.replace(matcher, persona_entity);
	    		 }
	    		 else {
	    			 LinkedHashMap<String, Object> persona_entity_tobeAdded = new LinkedHashMap<>();
	    			 persona_entity_tobeAdded.put("job_level",buyer.getJob_level());
	    			 persona_entity_tobeAdded.put("job_function", buyer.getJob_function());
	    			 persona_entity_tobeAdded.put("count", 1);
	    			 helper.put(matcher, persona_entity_tobeAdded);
	    		 }
	    	 }
	      }
	      List<LinkedHashMap<String,Object>> persona_count_resp = new ArrayList<LinkedHashMap<String,Object>>(helper.values()); 
		  return persona_count_resp;
	  }

	
	
	
	
	
	  @Cacheable(key = "#cachekey" , value = "activity_count")
	  public Map<String,Object> getActivityCount(Account account, String cachekey, String start, String end) throws ParseException{
		  System.out.println(" getActivity_count called");
		  Map<String,Object> activity_count_resp = new HashMap<String,Object>();
		  long adclicks = 0;
		  long website_visits = 0 ;
		  long form_fills = 0;
		  long live_chats = 0;
		  for(int i=0;i<account.getBuyer_list().size();i++) {
			  Buyer buyer = account.getBuyer_list().get(i);
			  for(int j=0;j<buyer.getActivity_list().size();j++) {
				  Activity activity = buyer.getActivity_list().get(j);
				  if(isValidDateToConsider(activity.getDatetime(), start, end)) {
					  if(activity.getActivity_type().equals("Website Visit")) {
						  website_visits++;
					  }
					  else if(activity.getActivity_type().equals("Ad Click")) {
						   adclicks++;
					  }
					  else if(activity.getActivity_type().equals("Form Fill")) {
						  form_fills++;
					  }
					  else if(activity.getActivity_type().equals("Live Chat")) {
						  live_chats++;
					  }
				  }
			  }
		  }
		  activity_count_resp.put("ad_clicks", adclicks);
		  activity_count_resp.put("website_visits", website_visits);
		  activity_count_resp.put("form_fills", form_fills);
		  activity_count_resp.put("live_chats", live_chats);
		  
		  return activity_count_resp;
	  }
	
	
	
	
	
	
	
	
	@Cacheable(key = "#id", value = "location_count")
	public List<LinkedHashMap<String,Object>> getLocation_Count(Account account, String id){
		System.out.println(" getLocation_count called");
		  Map<String, LinkedHashMap<String,Object>> helper = new LinkedHashMap<String, LinkedHashMap<String,Object>>();
	      for(int i=0; i<account.getBuyer_list().size(); i++) {
	    	 Buyer buyer = account.getBuyer_list().get(i);
	    	 if(!(buyer.getCity().equals("") || buyer.getState().equals("") || buyer.getCountry().equals(""))) {
	    		 String matcher = buyer.getCity() + buyer.getState() + buyer.getCountry();
	    		 if(helper.containsKey(matcher)) {
	    			 	LinkedHashMap<String, Object> location_entity = helper.get(matcher);
	    			 	location_entity.replace("count", ((int)location_entity.get("count") + 1));
	    			 	helper.replace(matcher, location_entity);
	    		 }
	    		 else {
	    			 LinkedHashMap<String, Object> location_entity_tobeAdded = new LinkedHashMap<>();
	    			 location_entity_tobeAdded.put("city",buyer.getCity());
	    			 location_entity_tobeAdded.put("state", buyer.getState());
	    			 location_entity_tobeAdded.put("country", buyer.getCountry());
	    			 location_entity_tobeAdded.put("count", 1);
	    			 helper.put(matcher, location_entity_tobeAdded);
	    		 }
	    	 }
	      }
	      List<LinkedHashMap<String,Object>> location_count_resp = new ArrayList<LinkedHashMap<String,Object>>(helper.values()); 
		  return location_count_resp;
	  }
	
	  public Date toDate(String date, String format) throws ParseException {
		  SimpleDateFormat f = new SimpleDateFormat(format);
		  Date d = new Date();
		  d = (f.parse(date));
		  return d;
	  }
	  
	  public boolean isValidDateToConsider( String eventDate , String start, String end) throws ParseException {
		  Date eventDate1 = new Date();
		  Date startDate = new Date();
		  Date endDate = new Date();
		  if(start != "") {
			  startDate = toDate(start, "MMddyyyy");  
		  }
		  if(end != "") {
			  endDate = toDate(end, "MMddyyyy");  
		  }
		  eventDate1 = toDate(eventDate, "yyyy-MM-dd HH:mm:ss" );
		  if(start == "" && end == "")
		  {
			  return true;
		  }
		  if(start == "" && eventDate1.after(startDate)) {
			  return true;
		  }
		  if(end == "" && eventDate1.before(endDate)) {
			  return true;
		  }
		  if(eventDate1.after(startDate) && eventDate1.before(endDate)) {
			  return true;
		  }
		  return false;
	  }
	
	
}
