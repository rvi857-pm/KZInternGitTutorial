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

	public Page<Account> accountServiceUtility(Account account, Integer page, Integer pageSize, String q) {
		if (page == null) {
			page = 1;
		}

		if (pageSize == null) {
			pageSize = 10;
		}

		if (q != null) {
			if (account.getName() == null && account.getIpDomain() == null && account.getCity() == null
					&& account.getState() == null && account.getCountry() == null && account.getType() == null
					&& account.getSalesforceId() == null) {
				return getUniversalSearchResults(page, pageSize, q);
			}
			return getMultiSearchResults(page, pageSize, account, q);
		}

		return getCompoundSearchResults(page, pageSize, account);
	}

	public List<Account> getAllResults() {
		return accountRepository.findAll();
	}

	@Cacheable(value = "universalSearchAccounts", key = "{#page,#pageSize,#q}")
	public Page<Account> getUniversalSearchResults(int page, int pageSize, String q) {
		Account account = new Account();
		account.setAll(q);
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		Example<Account> universalSearchExample = Example.of(account,
				generateExampleMatcher(ExampleMatcher.matchingAny()));
		return accountRepository.findAll(universalSearchExample, pageableInstance);
	}

	public Page<Account> getCompoundSearchResults(int page, int pageSize, Account account) {
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		Example<Account> compoundSearchExample = Example.of(account,
				generateExampleMatcher(ExampleMatcher.matchingAll()));
		return accountRepository.findAll(compoundSearchExample, pageableInstance);
	}

	public Page<Account> getMultiSearchResults(int page, int pageSize, Account accountCompound, String q) {
		Account accountUniversal = new Account();
		accountUniversal.setAll(q);
		Example<Account> universalSearchExample = Example.of(accountUniversal,
				generateExampleMatcher(ExampleMatcher.matchingAny()));
		List<Account> universalAccountList = accountRepository.findAll(universalSearchExample);

		Example<Account> compoundSearchExample = Example.of(accountCompound,
				generateExampleMatcher(ExampleMatcher.matchingAll()));
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

	private ExampleMatcher generateExampleMatcher(ExampleMatcher searchMatcher) {
		return searchMatcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());
	}
}
