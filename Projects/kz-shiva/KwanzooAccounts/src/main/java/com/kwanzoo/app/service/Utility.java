package com.kwanzoo.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.repo.AccountRepository;

@Component
public class Utility {

	@Autowired
	private AccountRepository accountRepo;

	public Account probe(Map<String, String> filter, boolean flag) {
		Account account = new Account();

		account.setName(flag ? filter.get("search") : filter.get("name"));
		account.setIpDomain(flag ? filter.get("search") : filter.get("ipDomain"));
		account.setCity(flag ? filter.get("search") : filter.get("city"));
		account.setState(flag ? filter.get("search") : filter.get("state"));
		account.setCountry(flag ? filter.get("search") : filter.get("country"));
		account.setType(flag ? filter.get("search") : filter.get("type"));
		account.setSalesforceId(flag ? filter.get("search") : filter.get("salesforceId"));
		return account;
	}

	public ExampleMatcher matcher(Map<String, String> filter) {
		return filter.get("search") != null ? ExampleMatcher.matchingAny() : ExampleMatcher.matchingAll();
	}

	public ExampleMatcher rules(ExampleMatcher obj) {
		return obj.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());
	}

	public List<Account> getList(Map<String, String> filter) {
		Example<Account> example = Example.of(probe(filter, false), rules(matcher(filter)));
		List<Account> list = accountRepo.findAll(example);
		if (filter.get("search") != null) {
			Example<Account> univExample = Example.of(probe(filter, true), rules(matcher(filter)));
			List<Account> univList = accountRepo.findAll(univExample);

			List<Account> result = list.stream().distinct().filter(univList::contains).collect(Collectors.toList());
			return result;
		} else
			return list;
	}

	public Page<Account> getPage(Map<String, String> filter, int page, int pageSize) {
		Example<Account> example = Example.of(probe(filter, false), rules(matcher(filter)));
		Pageable obj = PageRequest.of(page, pageSize);
		Page<Account> retVal = accountRepo.findAll(example, obj);
		return retVal;
	}
	
	public Page<Account> pageUtility(Pageable obj, List<Account> list) {
		int total = list.size();
		int start = (int) obj.getOffset();
		int end = Math.min((start + obj.getPageSize()), total);

		List<Account> output = new ArrayList<>();
		if (start <= end) output = list.subList(start, end);

		return new PageImpl<Account>(output, obj, total);
	}
}
