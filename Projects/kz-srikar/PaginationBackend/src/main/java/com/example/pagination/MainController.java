package com.example.pagination;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MainController {
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping(path = "/accounts")
	public Iterable<Account> search(@RequestParam Map<String, String> reqParam) {
		if (!reqParam.containsKey("page")) {
			return accountRepository.findAll();
		}
		int page = Integer.parseInt(reqParam.get("page"));
		int page_size = reqParam.containsKey("page_size") ? Integer.parseInt(reqParam.get("page_size")) : 10;

		if (reqParam.containsKey("name")) {
			String name = "%" + reqParam.get("name") + "%";
			return accountRepository.findBySingleSearch(name, (page - 1) * page_size, page_size);
		}

		Account account = new Account();

		if (reqParam.containsKey("account_name"))
			account.setAccount_name("%" + reqParam.get("account_name") + "%");
		else
			account.setAccount_name("%%");

		if (reqParam.containsKey("ip_domain"))
			account.setIp_domain("%" + reqParam.get("ip_domain") + "%");
		else
			account.setIp_domain("%%");

		if (reqParam.containsKey("ip_geo_city"))
			account.setIp_geo_city("%" + reqParam.get("ip_geo_city") + "%");
		else
			account.setIp_geo_city("%%");

		if (reqParam.containsKey("ip_geo_state"))
			account.setIp_geo_state("%" + reqParam.get("ip_geo_state") + "%");
		else
			account.setIp_geo_state("%%");

		if (reqParam.containsKey("ip_geo_country"))
			account.setIp_geo_country("%" + reqParam.get("ip_geo_country") + "%");
		else
			account.setIp_geo_country("%%");

		if (reqParam.containsKey("type"))
			account.setType("%" + reqParam.get("type") + "%");
		else
			account.setType("%%");

		if (reqParam.containsKey("sfdc_account_id"))
			account.setSfdc_account_id("%" + reqParam.get("sfdc_account_id") + "%");
		else
			account.setSfdc_account_id("%%");

		return accountRepository.findBysearch(account, (page - 1) * page_size, page_size);
	}

}