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
import com.kwanzoo.app.model.Account;

@Component
public class PageService {

	@Autowired
	private AccountPage accountPage;
	@Autowired
	private AccountList accountList;

	public Page<Account> execute(Map<String, String> filter) {

		if (filter.get("search") != null) {

			List<Account> list = accountList.getAccountList(filter);
			int page = Integer.parseInt(filter.get("page"));
			int size = Integer.parseInt(filter.get("page_size"));
			Pageable obj = PageRequest.of(page, size);
			return accountPage.pageUtility(obj, list);

		} else {

			int page = Integer.parseInt(filter.get("page"));
			int size = filter.get("page_size") != null ? Integer.parseInt(filter.get("page_size")) : 10;
			return accountPage.getPage(filter, page, size);

		}
	}

}
