package com.kwanzoo.app.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class AccountRepositoryImpl {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PageService pageService;
	@Autowired
	private MetricService metrics;

	@GetMapping(path = "/accounts")
	public Object getAccountByParams(@RequestParam Map<String, String> filter) {
		Data data = new Data();
		List<Account> accounts;

		if (filter.get("page") == null) {
			accounts = accountService.execute(filter);
		} else {
			Page<Account> page = pageService.execute(filter);
			data.setTotalElements((int)page.getTotalElements());
			accounts = page.getContent();
		}
		
		for (int i = 0; i < accounts.size(); i++) {

			Account account = accounts.get(i);
			CheckDate sample = new CheckDate();
			if(filter.get("start") != null) {
				String date = filter.get("start");
				int year = Integer.parseInt(date.substring(4, 8)) - 1900;
				int month = Integer.parseInt(date.substring(2, 4)) - 1;
				int day = Integer.parseInt(date.substring(0, 2));
				@SuppressWarnings("deprecation")
				Date first = new Date(year, month, day);
				sample.setStartDate(first);
			}
			if(filter.get("end") != null) {
				String date = filter.get("end");
				int year = Integer.parseInt(date.substring(4, 8)) - 1900;
				int day = Integer.parseInt(date.substring(2, 4)) - 1;
				int month = Integer.parseInt(date.substring(0, 2));
				@SuppressWarnings("deprecation")
				Date end = new Date(year, month, day);
				sample.setEndDate(end);
			}
			Metric metric = metrics.getMetrics(accounts.get(i), sample, accounts.get(i).getId(), filter.get("start"), filter.get("end"));
			data.addData(fillValues(filter, account, metric));

		}

		return data;

	}

	private Map<String, Object> fillValues(Map<String, String> filter, Account account, Metric metric) {

		Map<String, Object> value = new HashMap<String, Object>();
		value.put("name", account.getName());
		value.put("ipDomain", account.getIpDomain());
		value.put("city", account.getCity());
		value.put("state", account.getState());
		value.put("country", account.getCountry());
		value.put("type", account.getType());
		value.put("salesforceId", account.getSalesforceId());

		if (filter.get("metrics") != null) {
			String val = filter.get("metrics");
			if (val.contains("score") || val.contains("all"))
				value.put("score", metric.getScore());

			if (val.contains("marketing_qualified") || val.contains("all"))
				value.put("marketing_qualified", metric.isQualified());

			if (val.contains("buyer_count") || val.contains("all"))
				value.put("buyer_count", metric.getBuyerCount());

			if (val.contains("activity_count") || val.contains("all"))
				value.put("activity_count", metric.getActivityCount());

			if (val.contains("persona_count") || val.contains("all"))
				value.put("persona_count", metric.getPersonaCount());

			if (val.contains("location_count") || val.contains("all"))
				value.put("location_count", metric.getLocationCount());
		}

		return value;
	}

}
