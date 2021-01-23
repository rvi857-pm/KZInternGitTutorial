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
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String q,
			@RequestParam(required = false) String metrics) {

		if (page == null) {
			return accountService.getAllResults();
		}

		if (pageSize == null) {
			pageSize = 10;
		}

		if (q != null) {
			if (account.getName() == null && account.getIpDomain() == null && account.getCity() == null
					&& account.getState() == null && account.getCountry() == null && account.getType() == null
					&& account.getSalesforceId() == null) {
				return accountService.getUniversalSearchResults(page, pageSize, q);
			}
			return accountService.getMultiSearchResults(page, pageSize, account, q);
		}

		return accountService.getCompoundSearchResults(page, pageSize, account);
	}

}