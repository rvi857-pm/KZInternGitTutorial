package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@RestController
public class AccountController {

	@Autowired
	AccountRepository repo;
	
	@GetMapping("/")
	public String home(){
	
		return "home.jsp";
	}
	
	@GetMapping("/accounts")
	public  List<Account> getAccounts( @RequestParam Map<String, String> query ) {
		
		int page_no, page_size = 10;
		
		Pageable paging = null;
		if(!query.containsKey("page") || query.get("page") == "")
			paging = null;
		else {
			page_no = Integer.parseInt(query.get("page"));
			if (query.containsKey("page_size") && query.get("page_size") != ""){
				page_size = Integer.parseInt(query.get("page_size"));
			}
			paging = PageRequest.of(page_no - 1,page_size);
		}
			
		
		String city  = query.get("city");
		String country = query.get("country");
		String state = query.get("state");
		String name = query.get("name");
		Page<Account> pagedResult;
		if( name == null)
			pagedResult = repo.findAll(paging);
		else
			pagedResult = repo.findAllByNameContainingOrCityOrStateOrCountry(name,city,state,country,paging);
		
		return pagedResult.toList();
		
	}
	
}
