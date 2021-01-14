package com.example.pagination.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pagination.dao.AccountRepository;
import com.example.pagination.model.Account;

import java.util.Map;

@RestController
public class MainController {
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping(path = "/accounts")
	public Object search(@RequestParam Map<String, String> reqParam) {
		if (!reqParam.containsKey("page")) {
			return accountRepository.findAll();
		}
		int page = Integer.parseInt(reqParam.get("page"));
		int page_size = reqParam.containsKey("page_size") ? Integer.parseInt(reqParam.get("page_size")) : 10;

		if (reqParam.containsKey("name")) {
			String name = reqParam.get("name");
			Account account = new Account();
			account.setAll(name);
			Pageable pageableInstance = PageRequest.of(page - 1, page_size);
			Example<Account> universalSearchExample = Example.of(account, generateUniversalSearchMatcher());
			return accountRepository.findAll(universalSearchExample, pageableInstance);
		}

		Account account = new Account();

		account.setAccount_name(reqParam.get("account_name"));
		account.setIp_domain(reqParam.get("ip_domain"));
		account.setIp_geo_city(reqParam.get("ip_geo_city"));
		account.setIp_geo_state(reqParam.get("ip_geo_state"));
		account.setIp_geo_country(reqParam.get("ip_geo_country"));
		account.setType(reqParam.get("type"));
		account.setSfdc_account_id(reqParam.get("sfdc_account_id"));

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