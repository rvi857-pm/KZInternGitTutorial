package com.kwanzoo.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

	/**
	 * This method creates a Account object to probe the database. 
	 * This object is for universal search.
	 * @param filter
	 * @return probe to search the database
	 */
	private Account universalProbe(Map<String, String> filter) {
		Account account = new Account();

		account.setName(filter.get("search"));
		account.setIpDomain(filter.get("search"));
		account.setCity(filter.get("search"));
		account.setState(filter.get("search"));
		account.setCountry(filter.get("search"));
		account.setType(filter.get("search"));
		account.setSalesforceId(filter.get("search"));

		return account;
	}
	
	/**
	 * This method creates a Account object to probe the database.
	 * @param filter
	 * @return probe to search the database
	 */
	private Account probe(Map<String, String> filter) {
		Account account = new Account();

		account.setName(filter.get("name"));
		account.setIpDomain(filter.get("ipDomain"));
		account.setCity(filter.get("city"));
		account.setState(filter.get("state"));
		account.setCountry(filter.get("country"));
		account.setType(filter.get("type"));
		account.setSalesforceId(filter.get("id"));

		return account;
	}

	/**
	 * This method creates the rules to how the probing should be done.
	 * It is used for universal search.
	 * @return object with the rules.
	 */
	private ExampleMatcher universalRules() {
		ExampleMatcher expMatcher = ExampleMatcher.matchingAny()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());

		return expMatcher;
	}

	/**
	 * This method creates the rules to how the probing should be done.
	 * @return object with the rules.
	 */
	private ExampleMatcher rules() {
		ExampleMatcher expMatcher = ExampleMatcher.matchingAll()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());

		return expMatcher;
	}

	/**
	 * Utility method to get values from the database.
	 * @param filter
	 * @return list of the required data.
	 */
	private List<Account> utility(Map<String, String> filter) {
		Example<Account> example = Example.of(probe(filter), rules());
		List<Account> list = accRepo.findAll(example);
		if (filter.get("search") != null) {
			Example<Account> univExample = Example.of(universalProbe(filter), universalRules());
			List<Account> univList = accRepo.findAll(univExample);

			List<Account> result = list.stream().distinct().filter(univList::contains).collect(Collectors.toList());
			return result;
		} else
			return list;
	}

	/**
	 * Utility method to convert a list into a page.
	 * @param obj
	 * @param list
	 * @return page format of the required data
	 */
	private Page<Account> pageUtility(Pageable obj, List<Account> list) {
		int total = list.size();
		int start = (int) obj.getOffset();
		int end = Math.min((start + obj.getPageSize()), total);

		List<Account> output = new ArrayList<>();

		if (start <= end)
			output = list.subList(start, end);

		return new PageImpl<Account>(output, obj, total);
	}

	@GetMapping(path = "/accounts")
	@Cacheable("accounts")
	public Object getAccountByParams(@RequestParam Map<String, String> filter) {
		List<Account> list = utility(filter);
		if (filter.get("page_size") != null) {
			int page = Integer.parseInt(filter.get("page"));
			int size = Integer.parseInt(filter.get("page_size"));
			Pageable obj = PageRequest.of(page, size);
			return pageUtility(obj, list);
		} else if (filter.get("page") != null) {
			int page = Integer.parseInt(filter.get("page"));
			Pageable obj = PageRequest.of(page, 10);
			return pageUtility(obj, list);
		} else
			return utility(filter);

	}

}
