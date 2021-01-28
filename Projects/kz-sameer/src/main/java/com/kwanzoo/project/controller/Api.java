package com.kwanzoo.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.model.Buyer;
import com.kwanzoo.project.model.PageInfo;
import com.kwanzoo.project.model.PagedReturn;
import com.kwanzoo.project.service.ProjectService;

@RestController
public class Api {
	
	@Autowired
	ProjectService service;
	
	@GetMapping("/accounts")
	public PagedReturn getAccountsByPage( @ModelAttribute Account account, @ModelAttribute PageInfo pageInfo){
		
		String cacheKey = account.toString() + pageInfo.toString();
		
		return service.findBy(account, pageInfo, cacheKey);	
	}
	
	@GetMapping("/buyers")
	public Page<Buyer> getBuyers( @ModelAttribute Buyer buyer, @ModelAttribute PageInfo pageInfo){
		
		String cacheKey = buyer.toString() + pageInfo.toString();
		
		return service.findBuyerBy(buyer, pageInfo, cacheKey);	
	}

}
