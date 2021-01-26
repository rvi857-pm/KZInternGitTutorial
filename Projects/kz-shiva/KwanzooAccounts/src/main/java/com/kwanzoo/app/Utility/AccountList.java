package com.kwanzoo.app.Utility;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.repo.AccountRepository;

@Component
public class AccountList {

	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private Probe accountProbe;
	@Autowired
	private Matcher accountMatcher;

	public List<Account> getList(Map<String, String> filter) {
		Example<Account> example = Example.of(accountProbe.getProbe(filter, false),
				accountMatcher.getMatcher(filter, false));
		List<Account> list = accountRepo.findAll(example);
		if (filter.get("search") != null) {
			Example<Account> univExample = Example.of(accountProbe.getProbe(filter, true),
					accountMatcher.getMatcher(filter, true));
			List<Account> univList = accountRepo.findAll(univExample);

			List<Account> result = list.stream().distinct().filter(univList::contains).collect(Collectors.toList());
			return result;
		} else
			return list;
	}
}
