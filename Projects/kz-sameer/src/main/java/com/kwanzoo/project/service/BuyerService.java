package com.kwanzoo.project.service;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.kwanzoo.project.dao.BuyerDao;
import com.kwanzoo.project.model.Buyer;
import com.kwanzoo.project.model.PageInfo;

public class BuyerService {
	
	BuyerDao buyerRepo;
	
	int pageSize = 10;
	
   @Cacheable(cacheNames = "buyers", key="#cacheKey")
    public Page<Buyer> findBuyerBy(Buyer buyer, PageInfo pageInfo, String cacheKey){
    	
    	String any = pageInfo.getAny();
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
    	Page<Buyer> pagedBuyers;
        
    	if(any != null) {
    		
    		Buyer newBuyer = new Buyer(any);

    		ExampleMatcher matcherAny = ExampleMatcher.matchingAny().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    		ExampleMatcher matcherAll = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    		
    		
    		List<Buyer> buyerAll = buyerRepo.findAll(Example.of(buyer, matcherAll));
    		List<Buyer> buyerAny = buyerRepo.findAll(Example.of(newBuyer, matcherAny));
    		
    		buyerAll.retainAll(buyerAny);
    		
    		int start = (int) paging.getOffset();
    		int end = (start + paging.getPageSize()) > buyerAll.size() ? buyerAll.size() : (start + paging.getPageSize());
    		
    		pagedBuyers  = new PageImpl<Buyer>(buyerAll.subList(start, end), paging, buyerAll.size());
    		
    		return pagedBuyers;
    	}
    	
    	ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    	
    	pagedBuyers = buyerRepo.findAll(Example.of(buyer, matcher), paging);
    	
    	return pagedBuyers;
    }

}
