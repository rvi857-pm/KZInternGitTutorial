package com.kwanzoo.project.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kwanzoo.project.dao.BuyerDao;
import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.model.Buyer;
import com.kwanzoo.project.model.BuyerReturn;
import com.kwanzoo.project.model.PageInfo;
import com.kwanzoo.project.model.PagedReturn;

@Service
public class BuyerService {
	
	@Autowired
	BuyerDao buyerRepo;
	
	int pageSize = 10;
	
	MetricsUtility metricUtiity = new MetricsUtility();
	
	@Cacheable(cacheNames = "buyers", key="#cacheKey")
	public PagedReturn<BuyerReturn> findBuyers(Buyer buyer, String name, String id, PageInfo pageInfo, String cacheKey){
    	
    	String[] metrics = pageInfo.getMetrics();
    	
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
    	
    	buyer.setAccount(new Account(id, name, null, null, null, null, null, null, null));
    	
    	ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    	
    	
    	return metricUtiity.metricBuyer(buyerRepo.findAll(Example.of(buyer, matcher), paging), metrics, startDate, endDate, pageNo, pageSize);
    }

}
