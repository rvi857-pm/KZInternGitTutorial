package com.example.pagination.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pagination.dao.AccountRepository;
import com.example.pagination.model.Account;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	public Iterable<Account> getAllResults() {
		return accountRepository.findAll();
	}

	@Cacheable(value = "universalSearchAccounts", key = "{#page,#page_size,#name}")
	public Page<Account> getUniversalSearchResults(int page, int page_size, String name) {
		Account account = new Account();
		account.setAll(name);
		Pageable pageableInstance = PageRequest.of(page - 1, page_size);
		Example<Account> universalSearchExample = Example.of(account, generateUniversalSearchMatcher());
		return accountRepository.findAll(universalSearchExample, pageableInstance);
	}

	public Page<Account> getCompoundSearchResults(int page, int page_size, Account account) {
		Pageable pageableInstance = PageRequest.of(page - 1, page_size);
		Example<Account> compoundSearchExample = Example.of(account, generateCompoundSearchMatcher());
		return accountRepository.findAll(compoundSearchExample, pageableInstance);
	}

	private ExampleMatcher generateCompoundSearchMatcher() {
		ExampleMatcher compoundSearchMatcher = ExampleMatcher.matchingAll()
				.withMatcher("account_name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_domain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_geo_city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_geo_state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_geo_country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("sfdc_account_id", ExampleMatcher.GenericPropertyMatchers.contains());
		return compoundSearchMatcher;
	}

	private ExampleMatcher generateUniversalSearchMatcher() {
		ExampleMatcher universalSearchMatcher = ExampleMatcher.matchingAny()
				.withMatcher("account_name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_domain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_geo_city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_geo_state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ip_geo_country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("sfdc_account_id", ExampleMatcher.GenericPropertyMatchers.contains());
		return universalSearchMatcher;
	}

}
