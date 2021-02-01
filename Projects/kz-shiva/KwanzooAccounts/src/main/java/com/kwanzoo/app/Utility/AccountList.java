package com.kwanzoo.app.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.model.Activity;
import com.kwanzoo.app.model.Buyer;
import com.kwanzoo.app.repo.AccountRepository;
import com.kwanzoo.app.repo.BuyerRepository;

@Component
public class AccountList {

	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private BuyerRepository buyerRepo;
	@Autowired
	private Probe accountProbe;
	@Autowired
	private Matcher accountMatcher;

	public List<Account> getAccountList(Map<String, String> filter) {
		Example<Account> example = Example.of(accountProbe.getAccountProbe(filter, false),
				accountMatcher.getAccountMatcher(filter, false));
		List<Account> list = accountRepo.findAll(example);
		if (filter.get("search") != null) {
			Example<Account> univExample = Example.of(accountProbe.getAccountProbe(filter, true),
					accountMatcher.getAccountMatcher(filter, true));
			List<Account> univList = accountRepo.findAll(univExample);

			List<Account> result = list.stream().distinct().filter(univList::contains).collect(Collectors.toList());
			return result;
		} else
			return list;
	}
	
	public List<Buyer> getBuyerList(Map<String, String> filter) {
		List<Account> accounts = getAccountList(filter);
		List<Buyer> buyers = new ArrayList<Buyer>();
		for(int i = 0; i < accounts.size(); i++) {
			buyers.addAll(accounts.get(i).getBuyers());
		}
		return buyers;
	}
	
	public List<Activity>  getActivityList(Map<String, String> filter){
		Example<Buyer> example = Example.of(accountProbe.getBuyerProbe(filter));
		List<Buyer> list = buyerRepo.findAll(example);
		List<Activity> activities = new ArrayList<Activity>();
		for(int i = 0; i < list.size(); i++) {
			activities.addAll(list.get(i).getActivities());
		}
		return activities;
	}
}
