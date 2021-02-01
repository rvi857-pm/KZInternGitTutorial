package com.kwanzoo.app.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kwanzoo.app.Utility.CheckDate;
import com.kwanzoo.app.Utility.Data;
import com.kwanzoo.app.Utility.Metric;
import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.service.AccountService;
import com.kwanzoo.app.service.MetricService;
import com.kwanzoo.app.service.PageService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PageService pageService;

	@GetMapping(path = "/accounts")
	public Object getAccounts(@RequestParam Map<String, String> filter) {
		return accountService.accountData(filter);
	}

	@GetMapping(path = "/buyers")
	public Object getBuyers(@RequestParam Map<String, String> filter) {
		return accountService.buyerData(filter);
	}
	
//	@GetMapping(path = "/activities")
//	public Object getActivities(@RequestParam Map<String, String> filter) {
//		return accountService.activityData(filter);
//	}
	
}
