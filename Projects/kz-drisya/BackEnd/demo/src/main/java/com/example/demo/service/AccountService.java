package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@RestController
public class AccountService {

	@Autowired
	private AccountRepository repo;
	
	//getAll
	//getCompoundResults
	//getUniversalResults
	//getIntersectedResults
	
	private Pageable getPageUtils( Integer page_no, Integer page_size) {
		if ( page_no == null)
			return null;
		else if ( page_size == null)
			return PageRequest.of(page_no - 1,10);
		else
			return PageRequest.of(page_no - 1,page_size);
	}
	
	public ExampleMatcher rules(int type) {

		ExampleMatcher expMatcher;
		if ( type == 0)
			expMatcher = ExampleMatcher.matchingAny()
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
		else
			expMatcher = ExampleMatcher.matchingAll()
			.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
			.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
			.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
			.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
			.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
			.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains())
			.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
		return expMatcher;

	}

	public Object getAll(Integer page_no, Integer page_size){
		
		Pageable paging = getPageUtils( page_no, page_size);
		System.out.println(paging);
		if ( paging == null)
			return  repo.findAll();
		return repo.findAll(paging);
	}
	
	public Object getCompoundResults ( Account account, Integer page_no, Integer page_size) {
		
		System.out.println("com");
		Pageable paging = getPageUtils( page_no, page_size);
		
		Example<Account> accounts = Example.of(account, rules(1));
		if ( paging == null)
			return  repo.findAll(accounts);
		return repo.findAll(accounts, paging);
		
	}
	
	public Object getUniversalResults (String search, Integer page_no, Integer page_size) {
		System.out.println("univer");
		Pageable paging = getPageUtils(page_no, page_size);
		Account account = new Account(search);
		
		Example<Account> accounts = Example.of(account, rules(0));
		if ( paging == null)
			return repo.findAll(accounts);
		return repo.findAll(accounts, paging);
	}
	
	public Object getPageList ( List<Account> accounts, Integer page_no, Integer page_size) {
		
		Pageable paging = getPageUtils(page_no, page_size);
		if (paging == null)
			return accounts;
		
		int startIndex = (int)paging.getOffset();
		int list_length = accounts.size();
		int endIndex;
		if ( startIndex + page_size > list_length)
			endIndex = list_length;
		else
			endIndex = startIndex + page_size;
		
		return new PageImpl<Account>(accounts.subList(startIndex, endIndex), paging, list_length);
	}
	
	public Object getIntersectedResults( Account compoundAccount, String search, Integer page_no, Integer page_size) {
		
		System.out.println("intersect");
		Account universalAccount = new Account(search);
		
		Example<Account> univesalExmAccounts = Example.of(universalAccount, rules(0));
		Example<Account> compoundExmAccounts = Example.of(compoundAccount, rules(1));
		
		List<Account> universalAccounts = repo.findAll(univesalExmAccounts);
		List<Account> compoundAccounts = repo.findAll(compoundExmAccounts);
		
		Set<Account> result = compoundAccounts.stream()
				  .distinct()
				  .filter(universalAccounts::contains)
				  .collect(Collectors.toSet());
		
		List<Account> accounts =   new ArrayList<Account>(result);
		
		return getPageList(accounts, page_no, page_size);
	}
}
