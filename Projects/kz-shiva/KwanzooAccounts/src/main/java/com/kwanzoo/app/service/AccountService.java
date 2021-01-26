package com.kwanzoo.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.Utility.AccountList;
import com.kwanzoo.app.model.Account;

@Component
public class AccountService {

	@Autowired
	private AccountList accountList;

	public List<Account> execute(Map<String, String> filter) {
		return accountList.getList(filter);
	}
}
