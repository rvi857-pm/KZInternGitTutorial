package com.kwanzoo.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kwanzoo.project.dao.ActivityDao;
import com.kwanzoo.project.dao.BuyerDao;
import com.kwanzoo.project.dao.Dao;
import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.model.AccountReturn;
import com.kwanzoo.project.model.Activity;
import com.kwanzoo.project.model.ActivityCount;
import com.kwanzoo.project.model.Buyer;
import com.kwanzoo.project.model.LocationCount;
import com.kwanzoo.project.model.PageInfo;
import com.kwanzoo.project.model.PagedReturn;
import com.kwanzoo.project.model.PersonaCount;

@Service
public class ProjectService {
	
	    @Autowired
	    Dao repository;
	    
	    @Autowired
	    BuyerDao buyerRepo;
	    
	    @Autowired
	    ActivityDao activityRepo;
	    
	    int pageSize = 10;
	    
	    public List<AccountReturn> metric(String[] metrics, Page<Account> pagedAccounts, Date startDate, Date endDate, String[] exclude){
	    	
	    	boolean boolScore = false;
	    	boolean boolMarketing = false;
	    	boolean boolBuyerCount = false;
	    	boolean boolActivityCount = false;
	    	boolean boolPersonaCount = false;
	    	boolean boolLocationCount = false;
	    	boolean boolAll = false;
	    	
	    	if(metrics != null) {
		    	for(int i = 0; i < metrics.length ; i++) {
		    		if(metrics[i].equals("score")) {
		    			boolScore = true;
		    		}
		    		if(metrics[i].equals("marketing_qualified")) {
		    			boolMarketing = true;
		    		}
		    		if(metrics[i].equals("buyer_count")) {
		    			boolBuyerCount = true;
		    		}
		    		if(metrics[i].equals("activity_count")) {
		    			boolActivityCount = true;
		    		}
		    		if(metrics[i].equals("persona_count")) {
		    			boolPersonaCount = true;
		    		}
		    		if(metrics[i].equals("location_count")) {
		    			boolLocationCount = true;
		    		}
		    		if(metrics[i].equals("all")) {
		    			boolAll = true;
		    		}
		    	}
	    	}
	    	
	    	List<Account> accounts= pagedAccounts.getContent();
	    	
	    	List<AccountReturn> accountReturn = new ArrayList<>();
	    	
	    	for(Account temp : accounts) {
	    		
	    		List<Buyer> buyers = temp.getBuyers();
	    		
	    		Map<String,Integer> persona = new HashMap<String,Integer>();
	    		
	    		Map<String,Integer> locationCountMap = new HashMap<String,Integer>();
	    		
	    		Boolean marketingQualified = null;
	    		
	    		int accountScore = 0;
	    		
	    		int[] activityCount = {0, 0, 0, 0};
	    		
	    		int buyerCount = 0;
	    		
	    		int buyerScoreCount = 0;

	    		if(boolScore == true || boolAll == true || boolActivityCount == true || boolMarketing == true || boolBuyerCount == true || boolPersonaCount == true | boolLocationCount == true) {
		    		for(Buyer temp2 : buyers) {
		    			
		    			List<Activity> activities = temp2.getActivities();
		    			
		    			int buyerScore = 0;
		    			
		    			int sumActivityScore = 0;
		    			
		    			if(boolScore == true || boolAll == true || boolActivityCount == true || boolMarketing == true) {
			    			for(Activity temp3 : activities) {
			    				
			    				if(temp3.getDatetime().after(startDate) && temp3.getDatetime().before(endDate)) {
			    				
				    				String activityType = temp3.getActivityType();
				    				
				    				if(activityType.equals("Ad Click")) {
				    					sumActivityScore += 10;
				    					activityCount[0]++;
				    				}
				    				else if(activityType.equals("Website Visit")) {
				    					sumActivityScore += 1;
				    					activityCount[1]++;
				    				}
				    				else if(activityType.equals("Form Fill")) {
				    					sumActivityScore += 30;
				    					activityCount[2]++;
				    				}
				    				else if(activityType.equals("Live Chat")) {
				    					sumActivityScore += 30;
				    					activityCount[3]++;
				    				}
			    				}
			    			}
		    			}
		    			
		    			String jobLevel = temp2.getJobLevel();
		    			
		    			if(boolScore == true || boolAll == true || boolMarketing == true) {
			    			if(jobLevel.equals("C-Level")) {
			    				buyerScore = sumActivityScore * 200;
			    			}
			    			else if(jobLevel.equals("Owner,Board Member")) {
			    				buyerScore = sumActivityScore * 175;
			    			}
			    			else if(jobLevel.equals("VP,Director")) {
			    				buyerScore = sumActivityScore * 150;
			    			}
			    			else if(jobLevel.equals("Manager")) {
			    				buyerScore = sumActivityScore * 125;
			    			}
			    			else {
			    				buyerScore = sumActivityScore * 100;
			    			}
		    			}
		    			
		    			if(boolPersonaCount == true || boolAll == true) {
		    				
		    				String personaKey = jobLevel + "_" + temp2.getJobFunction();
			    			
			    			if(persona.containsKey(personaKey)) {
			    				persona.replace( personaKey, (int)persona.get(personaKey) + 1);
			    			}
			    			else {
			    				persona.put( personaKey, 1);
			    			}
		    			}
		    			
		    			if(boolLocationCount == true || boolAll == true) {
			    			String locationKey = temp2.getCity() + "_" + temp2.getState() + "_" + temp2.getCountry();
			    			
			    			if(locationCountMap.containsKey(locationKey)) {
			    				locationCountMap.replace( locationKey, locationCountMap.get(locationKey) + 1);
			    			}
			    			else {
			    				locationCountMap.put( locationKey, 1);
			    			}
		    			}
		    			
		    			if(buyerScore >= 4000) {
		    				buyerScoreCount++;
		    			}
		    			
		    			accountScore += buyerScore;
		    				    			
		    			buyerCount++;
		    		}
	    		}
	    		
	    		if(accountScore >= 10000 && buyerScoreCount >= 3) {
	    			marketingQualified = true;
	    		}else {
	    			marketingQualified = false;
	    		}
	    		
	    		List<LocationCount> locationCount = null;
	    		
	    		if(boolLocationCount == true || boolAll == true) {
	    			locationCount = new ArrayList<LocationCount>();
		    		for (Map.Entry<String,Integer> entry : locationCountMap.entrySet()) {
		    			String[] loc = entry.getKey().split("_");
		    			
		    			if(loc.length == 0) {
		    				continue;
		    			}
		    			if(loc.length == 1) {
		    				locationCount.add(new LocationCount(loc[0], null, null, entry.getValue()));
		    				continue;
		    			}
		    			if(loc.length == 2) {
		    				locationCount.add(new LocationCount(loc[0], loc[1], null, entry.getValue()));
		    				continue;
		    			}
		    			
		    			locationCount.add(new LocationCount(loc[0], loc[1], loc[2], entry.getValue()));
		    		
		    		}
	    		}
	    		
	    		List<PersonaCount> personaCount = null;
	    		
	    		if(boolPersonaCount == true || boolAll == true) {
	    			personaCount = new ArrayList<>();
		    		for (Map.Entry<String,Integer> entry : persona.entrySet()) {
		    			
		    			String[] per = entry.getKey().split("_");
		    			
		    			if(per.length == 0) {
		    				continue;
		    			}
		    			if(per.length == 1) {
		    				personaCount.add(new PersonaCount(per[0], "", entry.getValue()));
		    				continue;
		    			}
		    			
		    			personaCount.add(new PersonaCount(per[0], per[1], entry.getValue()));
		    		
		    		}
	    		}
	    		
	    		Float score = null;
	    		if(boolScore == true || boolAll == true) {
	    			score = (float)(accountScore/1000.0);
	    		}
	    		
	    		if(boolMarketing == false && boolAll == false) {
	    			marketingQualified = null;
	    		}
	    		
	    		ActivityCount activity = null;
	    		if(boolActivityCount == true || boolAll == true) {
	    			activity = new ActivityCount(activityCount[0], activityCount[1], activityCount[2], activityCount[3]);
	    		}
	    		
	    		Integer buyerCountObj = null;
	    		
	    		if(boolBuyerCount == true || boolAll == true) {
	    			buyerCountObj = buyerCount;
	    		}
	    		
	    		String id = temp.getId();
	    		String name = temp.getName();
	    		String ipDomain = temp.getIpDomain();
	    		String city = temp.getCity();
	    		String state = temp.getState();
	    		String country = temp.getCountry();
	    		String type = temp.getType();
	    		String salesforceId = temp.getSalesforceId();
	    		
	    		if(exclude != null) {
	    			for(int i = 0; i < exclude.length; i++) {
	    				if(exclude[i].equals("id")) {
	    					id = null;
	    				}
	    				else if(exclude[i].equals("name")) {
	    					name = null;
	    				}
	    				else if(exclude[i].equals("ipDomain")) {
	    					ipDomain = null;
	    				}
	    				else if(exclude[i].equals("city")) {
	    					city = null;
	    				}
	    				else if(exclude[i].equals("state")) {
	    					state = null;
	    				}
	    				else if(exclude[i].equals("country")) {
	    					country = null;
	    				}
	    				else if(exclude[i].equals("type")) {
	    					type = null;
	    				}
	    				else if(exclude[i].equals("salesforceId")) {
	    					salesforceId = null;
	    				}
	    			}
	    		}
	    		
	    		accountReturn.add( new AccountReturn(id, name, ipDomain, city, state, country, type, salesforceId, score ,
	    												marketingQualified, buyerCountObj, activity, personaCount, locationCount));
	    	}
	        
	    	return accountReturn;
	    	
	    }
	     
	    @Cacheable(cacheNames = "accounts", key="#cacheKey")
	    public PagedReturn findBy(Account account, PageInfo pageInfo, String cacheKey){
	    	
	    	String any = pageInfo.getAny();
	    	String[] metrics = pageInfo.getMetrics();
	    	String[] exclude = pageInfo.getExclude();
	    	
	    	int pageNo;
	    	if(pageInfo.getPage() != null) {
	    		pageNo = pageInfo.getPage();
	    	}else {
	    		pageNo = 0;
	    	}
	    	
	    	if(pageInfo.getPageSize() != null) {
	    		pageSize = pageInfo.getPageSize();
	    	}
	    	
	    	Date endDate = pageInfo.getEnd();
	    	if(endDate == null) {
	    		endDate = new Date();
	    	}
	    	
	    	Date startDate = pageInfo.getStart();
	    	if(startDate == null) {
	    		startDate = new Date(0);
	    	}
	    	
	    	Pageable paging = PageRequest.of(pageNo, pageSize);
	    	Page<Account> pagedAccounts;
	        
	    	if(any != null) {
	    		
	    		Account newAccount = new Account();
	    		newAccount.setAll(any);

	    		ExampleMatcher matcherAny = ExampleMatcher.matchingAny().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	    		ExampleMatcher matcherAll = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	    		
	    		
	    		List<Account> accountAll = repository.findAll(Example.of(account, matcherAll));
	    		List<Account> accountAny = repository.findAll(Example.of(newAccount, matcherAny));
	    		
	    		accountAll.retainAll(accountAny);
	    		
	    		int start = (int) paging.getOffset();
	    		int end = (start + paging.getPageSize()) > accountAll.size() ? accountAll.size() : (start + paging.getPageSize());
	    		
	    		pagedAccounts  = new PageImpl<Account>(accountAll.subList(start, end), paging, accountAll.size());
	    		
	    		return new PagedReturn(metric(metrics, pagedAccounts, startDate, endDate, exclude), pagedAccounts.isLast(), pagedAccounts.isFirst(), pagedAccounts.getTotalPages(), pagedAccounts.getNumber()); 
	    
	    	}
	    	
	    	ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	    	
	    	pagedAccounts = repository.findAll(Example.of(account, matcher), paging);
	    	
	    	return new PagedReturn(metric(metrics, pagedAccounts, startDate, endDate, exclude), pagedAccounts.isLast(), pagedAccounts.isFirst(), pagedAccounts.getTotalPages(), pagedAccounts.getNumber()); 
	    	
	    }
}
