package com.kwanzoo.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kwanzoo.project.dao.ActivityDao;
import com.kwanzoo.project.model.Activity;
import com.kwanzoo.project.model.ActivityReturn;
import com.kwanzoo.project.model.Buyer;
import com.kwanzoo.project.model.PagedReturn;

@Service
public class ActivityService {
	
	@Autowired
	ActivityDao activityRepo;
	
	int pageSize = 10;
	
	@Cacheable(cacheNames = "activity", key = "#cacheKey")
	public PagedReturn<ActivityReturn> findActivities(String id, int pageNo, int pageSize_, String cacheKey){
    	
		Activity activity = new Activity(null, null, null, null, null);
    	
    	if(pageSize_ != 0) {
    		pageSize = pageSize_;
    	}
    	
    	activity.setBuyer(new Buyer(id, null, null, null, null, null, null, null, null));
    	
    	ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    	
    	Pageable paging = PageRequest.of(pageNo, pageSize);
    	
    	Page<Activity> pagedActivities = activityRepo.findAll(Example.of(activity, matcher), paging);
    	
    	List<ActivityReturn> activityReturn = new ArrayList<>();
    	
    	for(Activity act : pagedActivities.getContent() ) {
    		activityReturn.add(new ActivityReturn(act.getActivityType(), act.getDatetime(), act.getDetails(), act.getBuyer().getId()));
    	}
    	
    	return new PagedReturn<>(activityReturn, pagedActivities.isLast(), pagedActivities.isFirst(), pagedActivities.getTotalPages(), pagedActivities.getNumber());
    }
	
}
