package com.example.pagination.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;

import com.example.pagination.model.Account;
import com.example.pagination.model.PageResponse;
import com.example.pagination.service.AccountService;
import com.example.pagination.service.MetricsService;

@RestController
public class MainController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MetricsService metricsService;

	@GetMapping(path = "/accounts")
	public PageResponse search(@ModelAttribute Account account, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String q,
			@RequestParam(required = false) List<String> metrics, @RequestParam(required = false) List<String> exclude,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end) {

		Page<Account> searchAccounts = accountService.accountServiceUtility(account, page, pageSize, q);

		return metricsService.metricsServiceUtility(searchAccounts, metrics, exclude);
	}

}