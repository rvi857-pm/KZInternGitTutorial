package com.kwanzoo.project.service;

import java.util.Date;
import java.util.List;

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
import com.kwanzoo.project.model.PageInfo;
import com.kwanzoo.project.model.PagedReturn;

@Service
public class ProjectService {
	
	    @Autowired
	    Dao repository;
	    
	    @Autowired
	    BuyerDao buyerRepo;
	    
	    @Autowired
	    ActivityDao activityRepo;
	    
	    int pageSize = 10;
	    int bPageSize = 10;
	    
	    MetricsUtility metricUtiity = new MetricsUtility();
	     
	    @Cacheable(cacheNames = "accounts", key="#cacheKey")
	    public PagedReturn<AccountReturn> findBy(Account account, PageInfo pageInfo, String cacheKey){
	    	
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
	    		
	    		return metricUtiity.metric(metrics, pagedAccounts, startDate, endDate, exclude, pageNo, pageSize); 
	    
	    	}
	    	
	    	ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	    	
	    	pagedAccounts = repository.findAll(Example.of(account, matcher), paging);
	    	
	    	return metricUtiity.metric(metrics, pagedAccounts, startDate, endDate, exclude, pageNo, pageSize); 
	    	
	    }
	   
	   public void addAccounts(List<Account> accounts) {	   
		   repository.saveAll(accounts);
	   }
}
	   
