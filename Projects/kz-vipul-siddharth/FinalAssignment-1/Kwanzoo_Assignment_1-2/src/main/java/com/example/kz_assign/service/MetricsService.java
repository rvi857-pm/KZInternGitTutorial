package com.example.kz_assign.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import com.example.kz_assign.KwanzooAssignment12Application;
import com.example.kz_assign.dao.Redis_repo;
import com.example.kz_assign.models.account;
import com.example.kz_assign.models.activity;
import com.example.kz_assign.models.buyer;

@Service
public class MetricsService {
	
	String startdate,enddate;
	boolean requestforall = false;
	
	private static Redis_repo redisrepo = new Redis_repo(KwanzooAssignment12Application.redisTemplate());
	
	private static Map<String, Integer> multipliermapping;
	
	static {
		multipliermapping = new LinkedCaseInsensitiveMap<>();
		multipliermapping.put("c-level", 200);
		multipliermapping.put("owner,board member", 175);
		multipliermapping.put("vp,director", 150);
		multipliermapping.put("manager", 125);
	}
	
	private static Map<String, Integer> activitymapping;
	
	static {
		activitymapping = new LinkedCaseInsensitiveMap<>();
		activitymapping.put("Ad Click", 10);
		activitymapping.put("Website Visit", 1);
		activitymapping.put("Form Fill", 30);
		activitymapping.put("Live Chat", 30);
	}

	
 	private Map<String, Object> toMap(account acc, List<String> exclude) {
	    Map<String, Object> result = new LinkedHashMap<String, Object>();
	    result.put("id", acc.getId().toString());
	    result.put("name", acc.getName());
	    result.put("ip_domain", acc.getIp_domain());
	    result.put("city", acc.getCity());
	    result.put("state", acc.getState());
	    result.put("country", acc.getCountry());
	    result.put("type", acc.getType());
	    result.put("salesforce_id", acc.getSalesforce_id());
	    for(String field:exclude) {
	    	result.remove(field);
	    }
	    return result;
	}
	
	List<Map<String,Object>> tomaplist(List<account> accountslist, List<String> exclude){
		 List<Map<String, Object>> datamap = new ArrayList<Map<String, Object>>();
		 
		 for(int i=0; i<accountslist.size(); i++) {
			 account acc = accountslist.get(i);
			 Map<String, Object> datamapsample = toMap(acc, exclude);
			 datamap.add(datamapsample);
		 }
		 
		 return datamap;
	}

	private Timestamp ConvertStrtoTS(String date) {
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date parsedDate = dateFormat.parse(date);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		    return timestamp;
		} catch(Exception e) { 
			e.printStackTrace();
			return null;
		}
	}
	
	
	private boolean comparedatetime(Timestamp date) {
		boolean result = true;
		if(startdate != null && !startdate.equals("")) {
			Timestamp start = ConvertStrtoTS(startdate);
			if(!start.equals(null)) {
				result =  date.after(start);
			}
			else {
				System.out.println("Error in start date");
				result =  false;
			}
		}
		if(enddate!= null && !enddate.equals("")) {
			Timestamp end = ConvertStrtoTS(enddate);
			if(!end.equals(null)) {
				result = (result & date.before(end));
			}
			else {
				System.out.println("Error in end date");
				result = result & false;
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void addactivityfields(account acc, Map<String,Object> datamapsample, String field, boolean activity_count){
		int accountscore = 0;
		boolean market_qualified = false;
		List<buyer> buyerslist = new ArrayList<buyer>(acc.getBuyers()); 
		Map<String,Integer> buyeractivities = new LinkedHashMap<>();
		Map<String,Object>redismapsample = redisrepo.findById(acc.getId());
		if(redismapsample==null | !(startdate.equals("")) | !(enddate.equals(""))) {
			int qualifiedbuyer =0 ;
			buyeractivities.put("Ad Click", 0);
			buyeractivities.put("Website Visit", 0);
			buyeractivities.put("Form Fill", 0);
			buyeractivities.put("Live Chat", 0);
			for(int i=0; i<buyerslist.size(); i++) {
				int buyer_score =0;
				int multiplier = multipliermapping.getOrDefault(buyerslist.get(i).getJob_level(), 100);
				List<activity> activitylist = new ArrayList<>(buyerslist.get(i).getActivities());
				for(activity a:activitylist) {
					if(comparedatetime(a.getDate())) {
						buyer_score += (activitymapping.getOrDefault(a.getType(), 1))*multiplier;
						int temp = buyeractivities.get(a.getType())+1;
						buyeractivities.replace(a.getType(), temp);
					}
				}
				if(buyer_score >= 4000)
					qualifiedbuyer+=1;
				accountscore += buyer_score;
			}
			if(qualifiedbuyer >3 & accountscore>=10000)
				market_qualified = true;
			if(startdate.equals("") & enddate.equals("")) {
				redismapsample = new LinkedHashMap<>();
				redismapsample.put("id", acc.getId());
				redismapsample.put("score", accountscore);
				redismapsample.put("market_qualified", market_qualified);
				redismapsample.put("activity_count", buyeractivities);
				redisrepo.save(redismapsample);
			}
		}else {
			accountscore = (int) redismapsample.get("score");
			buyeractivities = (Map<String, Integer>) redismapsample.get("activity_count");
			market_qualified = (boolean) redismapsample.get("market_qualified");
		}
		if(field.equals("score") | (field.equals("scoreandmq")))
			datamapsample.put("score", ((double)accountscore/(double)1000));
		if(field.equals("market_qualified") | (field.equals("scoreandmq")))
			datamapsample.put("market_qualified", market_qualified);
		if(activity_count)
			datamapsample.put("activity_count",buyeractivities);

	}

	private void addbuyer_countfield(account acc, Map<String,Object>datamapsample) {
		datamapsample.put("buyer_count", acc.getBuyers().size());
	}
		
	public void addpersona_countfield(account acc, Map<String,Object>datamapsample) {
		List<buyer> buyerslist = new ArrayList<buyer>(acc.getBuyers());
		List<Map<String,Object>> persona_countlist = new ArrayList<>();
		Map<String,Map<String,Object>> buyerpersonas = new LinkedHashMap<>();
		for	(buyer buy:buyerslist) {
			String job_level = buy.getJob_level();
			String job_function = buy.getJob_function();
			String buyerpersonakey = job_level + job_function;
			if(buyerpersonas.get(buyerpersonakey) != null) {
				Map<String, Object> bp = (Map<String, Object>) buyerpersonas.get(buyerpersonakey);
				int count = (int) bp.get("count");
				count = count +1 ;
				bp.replace("count", count);
				buyerpersonas.replace(buyerpersonakey, bp);
			}
			else {
				Map<String, Object> bp = new LinkedHashMap<>();
				bp.put("job_level", job_level);
				bp.put("job_function", job_function);
				bp.put("count", 1);
				buyerpersonas.put(buyerpersonakey, bp);
			}
		}
		for (Map<String,Object>bp : buyerpersonas.values())  
            persona_countlist.add(bp);
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
	
	
	public List<Map<String,Object>> addmetric(List<account> accountslist, String metric, List<Map<String,Object>> datamaplist, boolean activity_count ){
//		System.out.println(metric);
		
		for(int i=0; i<accountslist.size(); i++) {
//			redisrepo.delete(accountslist.get(i).getId());
			if(metric.equals("score") | metric.equals("scoreandmq") | metric.equals("market_qualified")) {
					addactivityfields(accountslist.get(i), datamaplist.get(i), metric, activity_count);
			}
			if(metric.equals("buyer_count")) {
					addbuyer_countfield(accountslist.get(i), datamaplist.get(i));
			}
//			if(metric.equals("activity_count")) {
//					addactivity_countfield(accountslist.get(i), datamaplist.get(i));
//			}
			if(metric.equals("persona_count")) {
					addpersona_countfield(accountslist.get(i), datamaplist.get(i));
			}
			if(metric.equals("location_count")) {
					addlocation_countfield(accountslist.get(i), datamaplist.get(i));
			}
		}
		return datamaplist;
	}
	
	private boolean processmetriclist(List<String> metrics) {
		if(metrics.contains("all")) {
			requestforall = true;
			metrics.remove(0);
			metrics.add("score");
			metrics.add("buyer_count");
			metrics.add("activity_count");
			metrics.add("persona_count");
			metrics.add("location_count");
			metrics.add("market_qualified");
		}else {
			requestforall = false;
		}
		if(metrics.contains("score")) {
			if(metrics.contains("market_qualified")) {
				metrics.remove("score");
				metrics.add("scoreandmq");
			}
		}else if(metrics.contains("market_qualified")) {
			if(metrics.contains("score")) {
				metrics.remove("market_qualified");
				metrics.add("scoreandmq");
			}
		}
		if(metrics.contains("activity_count")){
			metrics.remove("activity_count");
			return true;
		}
		return false;
	}
	
	public  List<Map<String,Object>> addmetrics(List<account> accountslist, List<String> metrics, String startdate, String enddate, List<String> exclude){
		
		this.startdate = startdate;
		this.enddate = enddate;
		boolean activity_count = false;
		if(metrics.size()>0)
			activity_count = processmetriclist(metrics);
		//System.out.println("metrics check:" + metrics);
		//System.out.println(accountslist.size());
		List<Map<String,Object>> datamaplist = tomaplist(accountslist, exclude);
		for(String metric:metrics) {
			datamaplist = addmetric(accountslist, metric,datamaplist,activity_count);
		}
			
		return datamaplist;
	}
	
}
	

