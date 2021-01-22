package com.kwanzoo.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.service.ProjectService;

@RestController
public class Api {
	
	@Autowired
	ProjectService service;
	
	@GetMapping("/accounts")
	public Page<Account> getAccountsByPage( @ModelAttribute Account account,
											@RequestParam(required = false, defaultValue = "0") int page,
											@RequestParam(required = false, defaultValue = "10") int pageSize,
											@RequestParam(required = false) String any){
		return service.findBy(account, page, pageSize, any);	
	}

}
