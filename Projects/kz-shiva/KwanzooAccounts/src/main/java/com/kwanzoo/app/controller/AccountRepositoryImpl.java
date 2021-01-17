package com.kwanzoo.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kwanzoo.app.repo.AccountRepository;

import com.kwanzoo.app.model.Account;

@RestController
public class AccountRepositoryImpl {

	@Autowired
	private AccountRepository accRepo;

	public Account probe(Map<String, String> filter) {

		Account account = new Account();
		if (filter.get("search") != null) {
			account.setName(filter.get("search"));
			account.setIpDomain(filter.get("search"));
			account.setCity(filter.get("search"));
			account.setState(filter.get("search"));
			account.setCountry(filter.get("search"));
			account.setType(filter.get("search"));
			account.setSalesforceId(filter.get("search"));
		} else {
			account.setName(filter.get("name"));
			account.setIpDomain(filter.get("ipDomain"));
			account.setCity(filter.get("city"));
			account.setState(filter.get("state"));
			account.setCountry(filter.get("country"));
			account.setType(filter.get("type"));
			account.setSalesforceId(filter.get("id"));
		}
		return account;

	}

	public ExampleMatcher rules(Map<String, String> filter) {

		ExampleMatcher expMatcher = filter.get("search") != null
				? ExampleMatcher.matchingAny().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains())
				: ExampleMatcher.matchingAll().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
						.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());

		return expMatcher;

	}

	@GetMapping(path = "/accounts")
	public List<Account> getAccountByParams(@RequestParam Map<String, String> filter) {

		Example<Account> accountExample = Example.of(probe(filter), rules(filter));
		return accRepo.findAll(accountExample);

	}

	@GetMapping(path = "/accounts", params = "page")
	public Page<Account> getAccountsByPage(@RequestParam int page, @RequestParam Map<String, String> filter) {

		Pageable obj = PageRequest.of(page, 10);
		Example<Account> accountExample = Example.of(probe(filter), rules(filter));
		return accRepo.findAll(accountExample, obj);

	}

	@GetMapping(path = "/accounts", params = { "page", "page_size" })
	public Page<Account> getAccountsByPage(@RequestParam int page, @RequestParam("page_size") int size,
			@RequestParam Map<String, String> filter) {

		Pageable obj = PageRequest.of(page, size);
		Example<Account> accountExample = Example.of(probe(filter), rules(filter));
		return accRepo.findAll(accountExample, obj);

	}

}
