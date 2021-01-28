package com.kwanzoo.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	@Cacheable("accounts")
	public Object getAccountByParams(@RequestParam Map<String, String> filter) {

		List<Account> accounts = accountService.execute(filter);
		List<Data> data = new ArrayList<Data>();

		for (int i = 0; i < accounts.size(); i++) {

			Account account = accounts.get(i);
			Metric metric = metrics.getMetrics(accounts.get(i));
			Data value = fillValues(filter, account, metric);
			data.add(value);

		}

		return data;

	}

	private Data fillValues(Map<String, String> filter, Account account, Metric metric) {

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
					value.put("buyer_count", metric.getScore());

			if (val.contains("activity_count") || val.contains("all"))
				value.put("activity_count", metric.getActivityCount());

			if (val.contains("persona_count") || val.contains("all"))
					value.put("persona_count", metric.getPersonaCount());

			if (val.contains("location_count") || val.contains("all"))
				value.put("location_count", metric.getLocationCount());
		}

		Data data = new Data();
		data.setData(value);
		return data;
	}

}
