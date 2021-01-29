package com.kwanzoo.project.service;

import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.kwanzoo.project.dao.ActivityDao;
import com.kwanzoo.project.model.Activity;
import com.kwanzoo.project.model.PageInfo;

public class ActivityService {
	
	ActivityDao activityRepo;
	
	int pageSize = 10;
	
    @Cacheable(cacheNames = "activities", key="#cacheKey")
    public Page<Activity> findActivityBy(Activity buyer, PageInfo pageInfo, String cacheKey){
    	
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
    	Page<Activity> pagedActivities;
    	
    	ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    	
    	pagedActivities = activityRepo.findAll(Example.of(buyer, matcher), paging);
    	
    	return pagedActivities;
    }
   
}
