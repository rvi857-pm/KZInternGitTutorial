package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	AccountService service;
	
	@GetMapping("/")
	public String home(){
	
		return "home.jsp";
	}

	
	@GetMapping("/accounts")
	@Cacheable (value="accounts")
	public Object getAccounts( @ModelAttribute Account account, @RequestParam (required = false) Integer page, @RequestParam (required = false) Integer page_size, @RequestParam (required = false) String q) {
	
	
		if ( q == null && account.isEmpty())
			return service.getAll(page, page_size);
		else if ( q == null )
			return service.getCompoundResults( account, page, page_size);
		else if ( account.isEmpty())
			return service.getUniversalResults( q, page, page_size);
		else
			return service.getIntersectedResults( account, q, page, page_size);
		
			
	}
	
}
