package com.kwanzoo.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kwanzoo.project.dao.Dao;
import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.model.AccountPage;

@Service
public class ProjectService {
	    @Autowired
	    Dao repository;
	    
	    Integer pageNo = 0;
	    Integer pageSize = 10;
	     
	    public List<Account> getAll(AccountPage accountpage)
	    {
	    	
	    	if(accountpage.getPage() != null) {
	    		pageNo = accountpage.getPage();
	    	}
	    	else {
	    		pageNo = 0;
	    	}
	    	if(accountpage.getPageSize() != null) {
	    		pageSize = accountpage.getPageSize();
	    	}
	    	
	        Pageable paging = PageRequest.of(pageNo, pageSize);
	 
	        Page<Account> pagedResult = repository.findAll(paging);
	         
	        if(pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<Account>();
	        }
	    }
	    
	    
	    public List<Account> findBy(AccountPage accountpage){
	    	
	    	Account account = (Account) accountpage;
	    	
	    	if(accountpage.getPage() != null) {
	    		pageNo = accountpage.getPage();
	    	}
	    	else {
	    		pageNo = 0;
	    	}
	    	if(accountpage.getPageSize() != null) {
	    		pageSize = accountpage.getPageSize();
	    	}
	    	
	        Pageable paging = PageRequest.of(pageNo, pageSize);
	        
	        List<Account> pagedResult = null;
	        
	        if(account.getName() != null) {
	        	pagedResult = repository.findByName(account.getName(), paging);
	        }
	        else if(account.getDomain() != null) {
	        	pagedResult = repository.findByDomain(account.getDomain(), paging);
	        }
	        else if(account.getCity() != null) {
	        	pagedResult = repository.findByCity(account.getCity(), paging);
	        }
	        else if(account.getState() != null) {
	        	pagedResult = repository.findByState(account.getState(), paging);
	        }
	        else if(account.getCountry() != null) {
	        	pagedResult = repository.findByCountry(account.getCountry(), paging);
	        }
	        else if(account.getType() != null) {
	        	pagedResult = repository.findByType(account.getType(), paging);
	        }
	        else if(account.getAccountId() != null) {
	        	pagedResult = repository.findByAccountId(account.getAccountId(), paging);
	        }else {
	        	return new ArrayList<Account>();
	        }
	         
	        if(!pagedResult.isEmpty()) {
	            return pagedResult;
	        } else {
	            return new ArrayList<Account>();
	        }
	    	
	    }
}
