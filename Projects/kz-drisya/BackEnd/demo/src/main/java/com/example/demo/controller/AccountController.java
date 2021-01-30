package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.MetricService;

@RestController
public class AccountController {

	@Autowired
	AccountService service;
	
	@Autowired
	MetricService metricService;
	
	@GetMapping("/")
	public String home(){
	
		return "home.jsp";
	}

	
	@GetMapping("/accounts")
	public List<Map<String,Object>> getAccounts( @ModelAttribute Account account, @RequestParam (required = false) Integer page, 
			@RequestParam (required = false) Integer page_size, @RequestParam (required = false) String q, @RequestParam ( required = false) String metrics, @RequestParam ( required = false) String start, @RequestParam ( required = false) String end, @RequestParam ( required = false) String exclude) {
		
		Page <Account> accounts= service.getAllAccounts( account, page, page_size, q);
		
		
		return metricService.metricsFilter(accounts, metrics,start, end,exclude);
		//return accounts;
			
	}
	
}
