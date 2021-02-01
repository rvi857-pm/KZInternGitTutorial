package com.kwanzoo.app.service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.Utility.AccountList;
import com.kwanzoo.app.Utility.CheckDate;
import com.kwanzoo.app.Utility.Data;
import com.kwanzoo.app.Utility.Metric;
import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.model.Activity;
import com.kwanzoo.app.model.Buyer;

@Component
public class AccountService {

	@Autowired
	private AccountList accountList;
	@Autowired
	private MetricService metricService;

	private CheckDate getCheckDate(Map<String, String> filter) {

		CheckDate sample = new CheckDate();
		if (filter.get("start") != null) {
			String date = filter.get("start");
			int year = Integer.parseInt(date.substring(4, 8)) - 1900;
			int month = Integer.parseInt(date.substring(2, 4)) - 1;
			int day = Integer.parseInt(date.substring(0, 2));
			@SuppressWarnings("deprecation")
			Date first = new Date(year, month, day);
			sample.setStartDate(first);
		}
		if (filter.get("end") != null) {
			String date = filter.get("end");
			int year = Integer.parseInt(date.substring(4, 8)) - 1900;
			int day = Integer.parseInt(date.substring(2, 4)) - 1;
			int month = Integer.parseInt(date.substring(0, 2));
			@SuppressWarnings("deprecation")
			Date end = new Date(year, month, day);
			sample.setEndDate(end);
		}
		return sample;

	}

	private void fillMetrics(Map<String, Object> value, String val, Metric metric, boolean flag) {
		if (val.contains("score") || val.contains("all"))
			value.put("score", metric.getScore());

		if (val.contains("marketing_qualified") || val.contains("all"))
			value.put("marketing_qualified", metric.isQualified());

		if (val.contains("activity_count") || val.contains("all"))
			value.put("activity_count", metric.getActivityCount());

		if (flag & (val.contains("buyer_count") || val.contains("all")))
			value.put("buyer_count", metric.getBuyerCount());

		if (flag & (val.contains("persona_count") || val.contains("all")))
			value.put("persona_count", metric.getPersonaCount());

		if (flag & (val.contains("location_count") || val.contains("all")))
			value.put("location_count", metric.getLocationCount());
	}

	private Map<String, Object> fillValues(Map<String, String> filter, Buyer buyer, Metric metric) {
		Map<String, Object> value = new HashMap<String, Object>();
		value.put("id", buyer.getId());
		value.put("account_id", buyer.getAccount().getId());
		value.put("account_name", buyer.getAccount().getName());
		value.put("job_level", buyer.getJobLevel());
		value.put("job_function", buyer.getJobFunction());
		value.put("city", buyer.getCity());
		value.put("state", buyer.getState());
		value.put("country", buyer.getCountry());
		value.put("source", buyer.getSource());

		if (filter.get("metrics") != null) {
			String val = filter.get("metrics");
			fillMetrics(value, val, metric, false);
		}

		return value;
	}

	private Map<String, Object> fillValues(Map<String, String> filter, Account account, Metric metric) {

		Map<String, Object> value = new HashMap<String, Object>();
		value.put("name", account.getName());
		value.put("ipDomain", account.getIp_domain());
		value.put("city", account.getCity());
		value.put("state", account.getState());
		value.put("country", account.getCountry());
		value.put("type", account.getType());
		value.put("salesforceId", account.getSalesforce_id());

		if (filter.get("metrics") != null) {
			String val = filter.get("metrics");
			fillMetrics(value, val, metric, true);
		}

		return value;
	}

	private Map<String, Object> fillValues(Map<String, String> filter, Activity activity) {

		Map<String, Object> value = new HashMap<String, Object>();

		return value;
	}

	public Data accountData(Map<String, String> filter) {
		Data data = new Data();
		List<Account> accounts = accountList.getAccountList(filter);
		CheckDate sample = getCheckDate(filter);

		for (int i = 0; i < accounts.size(); i++) {

			Account account = accounts.get(i);
			Metric metric = metricService.getMetrics(accounts.get(i), sample, accounts.get(i).getId(),
					filter.get("start"), filter.get("end"));
			data.addData(fillValues(filter, account, metric));

		}

		return data;
	}

	public Object buyerData(Map<String, String> filter) {
		Data data = new Data();
		List<Buyer> buyers = accountList.getBuyerList(filter);
		CheckDate sample = getCheckDate(filter);

		for (int i = 0; i < buyers.size(); i++) {
			Metric metric = metricService.getMetrics(buyers.get(i), sample, buyers.get(i).getId(), filter.get("start"),
					filter.get("end"));
			data.addData(fillValues(filter, buyers.get(i), metric));
		}
		return data;
	}

	public Object activityData(Map<String, String> filter) {
		Data data = new Data();
		List<Activity> activities = accountList.getActivityList(filter);
		for (int i = 0; i < activities.size(); i++) {
			data.addData(fillValues(filter, activities.get(i)));
		}
		return data;
	}
}
