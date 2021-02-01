package com.kwanzoo.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.Utility.AccountList;
import com.kwanzoo.app.Utility.AccountPage;
import com.kwanzoo.app.Utility.Data;
import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.model.Activity;
import com.kwanzoo.app.model.Buyer;

@Component
public class PageService {

	@Autowired
	private AccountPage accountPage;
	@Autowired
	private AccountList accountList;
	@Autowired
	private AccountService accountService;

	public Data accountData(Map<String, String> filter) {
		Data data = new Data();
		if (filter.get("search") != null) {

			List<Account> accounts = accountList.getAccountList(filter);
			int page = Integer.parseInt(filter.get("page"));
			int size = Integer.parseInt(filter.get("page_size"));
			Pageable obj = PageRequest.of(page, size);
			Page<Account> res = accountPage.getAccountPage(obj, accounts);
			data.setTotalElements((int) res.getTotalElements());
			return accountService.accountUtil(filter, res.getContent(), data);

		} else {

			int page = Integer.parseInt(filter.get("page"));
			int size = filter.get("page_size") != null ? Integer.parseInt(filter.get("page_size")) : 10;
			Page<Account> res = accountPage.getAccountPage(filter, page, size);
			data.setTotalElements((int) res.getTotalElements());
			return accountService.accountUtil(filter, res.getContent(), data);
		}
	}

	public Data buyerData(Map<String, String> filter) {
		Data data = new Data();
		List<Buyer> buyers = accountList.getBuyerList(filter);
		int page = Integer.parseInt(filter.get("page"));
		int size = Integer.parseInt(filter.get("page_size"));
		Pageable obj = PageRequest.of(page, size);
		Page<Buyer> res = accountPage.getBuyerPage(obj, buyers);
		data.setTotalElements((int) res.getTotalElements());
		return accountService.buyerUtil(filter, res.getContent(), data);
	}

	public Data activityData(Map<String, String> filter) {
		Data data = new Data();
		List<Activity> activities = accountList.getActivityList(filter);
		int page = Integer.parseInt(filter.get("page"));
		int size = Integer.parseInt(filter.get("page_size"));
		Pageable obj = PageRequest.of(page, size);
		Page<Activity> res = accountPage.getActivityPage(obj, activities);
		data.setTotalElements((int) res.getTotalElements());
		return accountService.activityUtil(filter, res.getContent(), data);
	}

}
