package com.kwanzoo.app.service;

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

	public Data accountUtil(Map<String, String> filter, List<Account> accounts, Data data) {
		CheckDate sample = metricService.getCheckDate(filter);
		for (int i = 0; i < accounts.size(); i++) {

			Account account = accounts.get(i);
			Metric metric = metricService.getMetrics(accounts.get(i), sample, accounts.get(i).getId(),
					filter.get("start"), filter.get("end"));
			data.addData(metricService.fillValues(filter, account, metric));

		}
		return data;
	}
	
	public Data buyerUtil(Map<String, String> filter, List<Buyer> buyers, Data data) {
		CheckDate sample = metricService.getCheckDate(filter);

		for (int i = 0; i < buyers.size(); i++) {
			Metric metric = metricService.getMetrics(buyers.get(i), sample, buyers.get(i).getId(), filter.get("start"),
					filter.get("end"));
			data.addData(metricService.fillValues(filter, buyers.get(i), metric));
		}
		return data;
	}
	
	public Data activityUtil(Map<String, String> filter, List<Activity> activities, Data data) {
		for (int i = 0; i < activities.size(); i++) {
			data.addData(metricService.fillValues(filter, activities.get(i)));
		}
		return data;
	}
	
	public Data accountData(Map<String, String> filter) {
		Data data = new Data();
		List<Account> accounts = accountList.getAccountList(filter);
		return accountUtil(filter, accounts, data);
	}

	public Data buyerData(Map<String, String> filter) {
		Data data = new Data();
		List<Buyer> buyers = accountList.getBuyerList(filter);
		
		return buyerUtil(filter, buyers, data);
	}

	public Object activityData(Map<String, String> filter) {
		Data data = new Data();
		List<Activity> activities = accountList.getActivityList(filter);
		return activityUtil(filter, activities, data);
	}
}
