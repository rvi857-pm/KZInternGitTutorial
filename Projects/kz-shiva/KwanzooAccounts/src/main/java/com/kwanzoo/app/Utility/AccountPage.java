package com.kwanzoo.app.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.repo.AccountRepository;

@Component
public class AccountPage {

	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private Probe accountProbe;
	@Autowired
	private Matcher accountMatcher;

	public Page<Account> getPage(Map<String, String> filter, int page, int pageSize) {
		Example<Account> example = Example.of(accountProbe.getProbe(filter, false),
				accountMatcher.getMatcher(filter, false));
		Pageable obj = PageRequest.of(page, pageSize);
		Page<Account> retVal = accountRepo.findAll(example, obj);
		return retVal;
	}

	public Page<Account> pageUtility(Pageable obj, List<Account> list) {
		int total = list.size();
		int start = (int) obj.getOffset();
		int end = Math.min((start + obj.getPageSize()), total);

		List<Account> output = new ArrayList<>();
		if (start <= end)
			output = list.subList(start, end);

		return new PageImpl<Account>(output, obj, total);
	}
}
