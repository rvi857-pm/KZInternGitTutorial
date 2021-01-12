package com.kwanzoo.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.model.AccountPage;
import com.kwanzoo.project.service.ProjectService;

@RequestMapping("/")
@RestController
public class Api {
	
	@Autowired
	ProjectService service;

	@GetMapping("/accounts")
	public List<Account> getAllAccounts(@ModelAttribute AccountPage accountpage){
		return (List<Account>) service.getAll(accountpage);
	}
	
	@PostMapping("/accounts")
	public List<Account> getAccountsByPage( @ModelAttribute AccountPage accountpage){
		return (List<Account>) service.findBy(accountpage);	
	}

}
