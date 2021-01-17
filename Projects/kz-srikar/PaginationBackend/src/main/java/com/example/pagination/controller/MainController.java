package com.example.pagination.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pagination.model.Account;
import com.example.pagination.service.AccountService;

@RestController
public class MainController {

	@Autowired
	private AccountService accountService;

	@GetMapping(path = "/accounts")
	public Object search(@ModelAttribute Account account, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer page_size, @RequestParam(required = false) String name) {

		if (page == null) {
			return accountService.getAllResults();
		}

		if (page_size == null) {
			page_size = 10;
		}

		if (name != null) {
			if (account.isNull()) {
				return accountService.getUniversalSearchResults(page, page_size, name);
			}
			return accountService.getMultiSearchResults(page, page_size, account, name);
		}

		return accountService.getCompoundSearchResults(page, page_size, account);
	}

}