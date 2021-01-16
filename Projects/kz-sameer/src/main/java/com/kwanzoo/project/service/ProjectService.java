package com.kwanzoo.project.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kwanzoo.project.dao.Dao;
import com.kwanzoo.project.model.Account;

@Service
public class ProjectService {
	    @Autowired
	    Dao repository;
	    
	    Integer pageNo = 0;
	    Integer pageSize = 10;
	     
	    public Page<Account> getAll(Integer page, Integer pageSize_)
	    {
	    	
	    	if(page != null) {
	    		pageNo = page;
	    	}
	    	else {
	    		pageNo = 0;
	    	}
	    	if(pageSize_ != null) {
	    		pageSize = pageSize_;
	    	}
	    	
	        Pageable paging = PageRequest.of(pageNo, pageSize);
	 
	        return repository.findAll(paging);
	    }
	    
	    
	    public Page<Account> findBy(Account account, Integer page, Integer pageSize_, String any){

	    	if(page != null) {
	    		pageNo = page;
	    	}
	    	else {
	    		pageNo = 0;
	    	}
	    	if(pageSize_ != null) {
	    		pageSize = pageSize_;
	    	}
	    	
	        Pageable paging = PageRequest.of(pageNo, pageSize);
	        
	    	if(any != null) {
	    		Account newAccount = new Account();
	    		newAccount.setALL(any);

	    		ExampleMatcher matcherAny = ExampleMatcher.matchingAny().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	    		ExampleMatcher matcherAll = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	    		
	    		
	    		List<Account> accountAll = repository.findAll(Example.of(account, matcherAll));
	    		List<Account> accountAny = repository.findAll(Example.of(newAccount, matcherAny));
	    		
	    		accountAll.retainAll(accountAny);
	    		
	    		int start = (int) paging.getOffset();
	    		int end = (start + paging.getPageSize()) > accountAll.size() ? accountAll.size() : (start + paging.getPageSize());
	    		
	    		Page<Account> pagedResult  = new PageImpl<Account>(accountAll.subList(start, end), paging, accountAll.size());
	    		
	    		return pagedResult;
	    	}
	    	
	    	ExampleMatcher matcher = ExampleMatcher.matchingAll().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
	        
	        return repository.findAll(Example.of(account, matcher), paging);
	    	
	    }
}
