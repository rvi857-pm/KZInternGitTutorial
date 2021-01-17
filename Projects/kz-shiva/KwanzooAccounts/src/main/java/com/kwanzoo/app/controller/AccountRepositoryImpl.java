package com.kwanzoo.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	
	private List<Account> utility(Map<String, String> filter) {
		System.out.println("he");
		Example<Account> example = Example.of(probe(filter), rules());
		Example<Account> univExample = Example.of(universalProbe(filter), universalRules());
		List<Account> list = accRepo.findAll(example);
		List<Account> univList = accRepo.findAll(univExample);
		
		Set<Account> result = list.stream()
				  .distinct()
				  .filter(univList::contains)
				  .collect(Collectors.toSet());
		
		List<Account> aList = new ArrayList<Account>(result);
		return aList;
	}
	
	private Page<Account> pageUtility(Pageable obj, List<Account> list){
		int total = list.size();
		int start = (int)obj.getOffset();
		System.out.println(start);
		int end = Math.min((start + obj.getPageSize()), total);
		System.out.println(end);

		List<Account> output = new ArrayList<>();

		if (start <= end) output = list.subList(start, end);

		return new PageImpl<Account>(output, obj, total);
	}

	@GetMapping(path = "/accounts")
	@Cacheable("accounts")
	public List<Account> getAccountByParams(@RequestParam Map<String, String> filter) {
		return utility(filter);
	}

	@GetMapping(path = "/accounts", params = "page")
	public Page<Account> getAccountsByPage(@RequestParam int page, @RequestParam Map<String, String> filter) {

		List<Account> list = utility(filter);
		Pageable obj = PageRequest.of(page, 10);
		return pageUtility(obj, list);

	}

	@GetMapping(path = "/accounts", params = { "page", "page_size" })
	public Page<Account> getAccountsByPage(@RequestParam int page, @RequestParam("page_size") int size,
			@RequestParam Map<String, String> filter) {

		List<Account> list = utility(filter);
		Pageable obj = PageRequest.of(page, size);
		return pageUtility(obj, list);

	}

}
