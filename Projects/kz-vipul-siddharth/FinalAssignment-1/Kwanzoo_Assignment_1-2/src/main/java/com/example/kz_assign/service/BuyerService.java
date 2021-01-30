package com.example.kz_assign.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kz_assign.dao.buyer_repo;
import com.example.kz_assign.models.account;
import com.example.kz_assign.models.activity;
import com.example.kz_assign.models.buyer;

@Service
public class BuyerService {
	@Autowired
	buyer_repo brepo;
	
//	public int getbuyerscore(buyer b) {
//		int buyer_score = 0;
//		
//		return 
//	}
	
	private Map<String, Object> toMap(buyer b, List<String> exclude) {
	    Map<String, Object> result = new LinkedHashMap<String, Object>();
	    account a = b.getAcc();
	    result.put("id", b.getBuyer_id().toString());
	    result.put("name", a.getName());
	    result.put("account_id", b.getAccountid());
	    result.put("job_level", b.getJob_level());
	    result.put("job_function", b.getJob_function());
	    result.put("city", b.getCity());
	    result.put("state", b.getState());
	    result.put("country", b.getCountry());
	    result.put("source", b.getSource());
	    for(String field:exclude) {
	    	result.remove(field);
	    }
	    return result;
	}
	
	public List<Map<String,Object>> tomaplist(List<buyer> buyerslist, List<String> exclude){
		 List<Map<String, Object>> buyerdatamap = new ArrayList<Map<String, Object>>();
		 
		 for(buyer b:buyerslist) {
			 Map<String, Object> datamapsample = toMap(b, exclude);
			 buyerdatamap.add(datamapsample);
		 }
		 
		 return buyerdatamap;
	}
	
	public List<Map<String,Object>> getallbuyersmap(List<String>exclude){
		return tomaplist(brepo.findAll(),exclude);
	}
	
	public List<buyer> getallbuyerslist(){
		return brepo.findAll();
	}

	public Set<activity> getbuyeractivities(String buyer_id){
		Optional<buyer> bopt = brepo.findById(buyer_id); 
		buyer b;
		try {
		b = bopt.get();
		}catch(Exception e) {
			throw new RuntimeException("Not a correct buyer id");
		}
		Set<activity>activityset = b.getActivities();
		return activityset;
	}
}
