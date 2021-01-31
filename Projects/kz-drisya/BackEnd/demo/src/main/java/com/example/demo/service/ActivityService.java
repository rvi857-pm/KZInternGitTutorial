package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Activity;
import com.example.demo.repository.ActivityRepository;

@Service
public class ActivityService {
	
	//getActivity
	@Autowired
	AccountService service;
	
	@Autowired
	ActivityRepository repo;
	
	
	public Page<Activity> getActivity (Map<String, String> Query, Integer page_no, Integer page_size ){
		
		String buyerId = Query.get("buyer_id");
		Pageable paging = service.getPageUtils(page_no, page_size);
		
		if( buyerId == null)
			return repo.findAll(paging);
		else
			return repo.findAllByBuyerId(buyerId, paging);
	}

}
