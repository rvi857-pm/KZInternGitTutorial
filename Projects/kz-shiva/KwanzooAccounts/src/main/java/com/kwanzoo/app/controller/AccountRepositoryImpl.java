package com.kwanzoo.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Account probe( Map<String, String> filter ) {
		
		// Creates an probe object based on the filter1.
		Account account = new Account();

		account.setAccountName(filter.get("name"));
		account.setIpDomain(filter.get("domain"));
		account.setIpGeoCity(filter.get("city"));
		account.setIpGeoState(filter.get("state"));
		account.setIpGeoCountry(filter.get("country"));
		account.setType(filter.get("type"));
		account.setSfdcAccountId(filter.get("id"));
		
		return account;
		
	}
	
	public ExampleMatcher rules() {

		ExampleMatcher expMatcher = ExampleMatcher.matchingAll()
				.withMatcher("accountName", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipGeoCity", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipGeoState", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipGeoCountry", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("sfdcAccountId", ExampleMatcher.GenericPropertyMatchers.contains());
		
		return expMatcher;
		
	}

	@GetMapping(path = "/accounts")
	public List<Account> getAccountByParams( @RequestParam Map<String, String> filter ) {		

		Example<Account> accountExample = Example.of(probe(filter), rules());
		return accRepo.findAll(accountExample);

	}

	@GetMapping(path = "/accounts", params = "page")
	public Page<Account> getAccountsByPage(@RequestParam int page, @RequestParam Map<String, String> filter) {
		
		Pageable obj = PageRequest.of(page, 10);
		Example<Account> accountExample = Example.of(probe(filter), rules());
		return accRepo.findAll(accountExample, obj);

	}
	
	@GetMapping(path = "/accounts", params = {"page", "page_size"})
	public Page<Account> getAccountsByPage(@RequestParam int page, @RequestParam("page_size") int size, @RequestParam Map<String, String> filter) {
		
		Pageable obj = PageRequest.of(page, size);
		Example<Account> accountExample = Example.of(probe(filter), rules());
		return accRepo.findAll(accountExample, obj);

	}


}
