package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repo;
	
	//getAll
	//getCompoundResults
	//getUniversalResults
	//getIntersectedResults
	
	public Page<Account> getAllAccounts( Account account, Integer page, Integer page_size, String q) {
		
		if ( q == null && account.isEmpty())
			return getAll(page, page_size);
		else if ( q == null )
			return getCompoundResults( account, page, page_size);
		else if ( account.isEmpty())
			return getUniversalResults( q, page, page_size);
		else
			return getIntersectedResults( account, q, page, page_size);
		
	}
	
	private Page<Account> getAll(Integer page_no, Integer page_size){
		
		Pageable paging = getPageUtils( page_no, page_size);
		System.out.println(paging);
		return repo.findAll(paging);
	}
	
	private Page<Account> getCompoundResults ( Account account, Integer page_no, Integer page_size) {
		
		System.out.println("com");
		Pageable paging = getPageUtils( page_no, page_size);
		
		Example<Account> accounts = Example.of(account, rules(1));
		return repo.findAll(accounts, paging);
		
	}
	
	private Page<Account> getUniversalResults (String search, Integer page_no, Integer page_size) {
		System.out.println("univer");
		Pageable paging = getPageUtils(page_no, page_size);
		Account account = new Account(search);
		
		Example<Account> accounts = Example.of(account, rules(0));
		
		return repo.findAll(accounts, paging);
	}
	
	
	private Page<Account> getIntersectedResults( Account compoundAccount, String search, Integer page_no, Integer page_size) {
		
		System.out.println("intersect");
		Account universalAccount = new Account(search);
		
		Example<Account> univesalExmAccounts = Example.of(universalAccount, rules(0));
		Example<Account> compoundExmAccounts = Example.of(compoundAccount, rules(1));
		
		List<Account> universalAccounts = repo.findAll(univesalExmAccounts);
		List<Account> compoundAccounts = repo.findAll(compoundExmAccounts);
		
		List<Account> accounts = universalAccounts.stream().distinct().filter(compoundAccounts::contains)
				.collect(Collectors.toList());
		
		
		return getPageList(accounts, page_no, page_size);
	}
	
	private Page<Account> getPageList ( List<Account> accounts, Integer page_no, Integer page_size) {
		
		Pageable paging = getPageUtils(page_no, page_size);
		
		int startIndex = (int)paging.getOffset();
		int list_length = accounts.size();
		int endIndex;
		if ( startIndex + page_size > list_length)
			endIndex = list_length;
		else
			endIndex = startIndex + page_size;
		
		return new PageImpl<Account>(accounts.subList(startIndex, endIndex), paging, list_length);
	}
	
	public Pageable getPageUtils( Integer page_no, Integer page_size) {
		if ( page_no == null)
			page_no = 1;
		
		if ( page_size == null)
			page_size = 10;
		
		return PageRequest.of(page_no - 1,page_size);
	}
	
	private   ExampleMatcher selectMatcher(int type) {
		if ( type == 0)
			return ExampleMatcher.matchingAny();
		else
			return ExampleMatcher.matchingAll();
	}
	public ExampleMatcher rules(int type) {

		ExampleMatcher expMatcher = selectMatcher(type)
				.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains());
		return expMatcher;

	}

}
