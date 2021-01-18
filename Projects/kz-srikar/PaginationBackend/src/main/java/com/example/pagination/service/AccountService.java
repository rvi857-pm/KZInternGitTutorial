package com.example.pagination.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

	@Cacheable(value = "universalSearchAccounts", key = "{#page,#pageSize,#q}")
	public Page<Account> getUniversalSearchResults(int page, int pageSize, String q) {
		Account account = new Account();
		account.setAll(q);
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		Example<Account> universalSearchExample = Example.of(account, generateUniversalSearchMatcher());
		return accountRepository.findAll(universalSearchExample, pageableInstance);
	}

	public Page<Account> getCompoundSearchResults(int page, int pageSize, Account account) {
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		Example<Account> compoundSearchExample = Example.of(account, generateCompoundSearchMatcher());
		return accountRepository.findAll(compoundSearchExample, pageableInstance);
	}

	public Page<Account> getMultiSearchResults(int page, int pageSize, Account accountCompound, String q) {
		Account accountUniversal = new Account();
		accountUniversal.setAll(q);
		Example<Account> universalSearchExample = Example.of(accountUniversal, generateUniversalSearchMatcher());
		List<Account> universalAccountList = accountRepository.findAll(universalSearchExample);

		Example<Account> compoundSearchExample = Example.of(accountCompound, generateCompoundSearchMatcher());
		List<Account> compoundAccountList = accountRepository.findAll(compoundSearchExample);

		List<Account> multiSearchList = universalAccountList.stream().distinct().filter(compoundAccountList::contains)
				.collect(Collectors.toList());
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		return pageUtility(pageableInstance, multiSearchList);
	}

	// https://stackoverflow.com/questions/37136679/how-to-convert-a-list-of-enity-object-to-page-object-in-spring-mvc-jpa/46765495
	private Page<Account> pageUtility(Pageable obj, List<Account> list) {
		int total = list.size();
		int start = (int) obj.getOffset();
		int end = Math.min((start + obj.getPageSize()), total);

		List<Account> output = new ArrayList<>();
		if (start <= end)
			output = list.subList(start, end);

		return new PageImpl<Account>(output, obj, total);
	}

	private ExampleMatcher generateCompoundSearchMatcher() {
		ExampleMatcher compoundSearchMatcher = ExampleMatcher.matchingAll()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());
		return compoundSearchMatcher;
	}

	private ExampleMatcher generateUniversalSearchMatcher() {
		ExampleMatcher universalSearchMatcher = ExampleMatcher.matchingAny()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());
		return universalSearchMatcher;
	}

}
