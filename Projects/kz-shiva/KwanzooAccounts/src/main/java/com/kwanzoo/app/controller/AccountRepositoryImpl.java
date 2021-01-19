package com.kwanzoo.app.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kwanzoo.app.service.AccountService;
import com.kwanzoo.app.service.PageService;

@RestController
public class AccountRepositoryImpl {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PageService pageService;

	@GetMapping(path = "/accounts")
	@Cacheable("accounts")
	public Object getAccountByParams(@RequestParam Map<String, String> filter) {
		
		return filter.get("page") != null ? pageService.execute(filter) : accountService.execute(filter);

	}

}
