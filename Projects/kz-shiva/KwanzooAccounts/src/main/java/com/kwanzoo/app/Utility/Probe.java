package com.kwanzoo.app.Utility;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;

@Component
public class Probe {

	public Account getProbe(Map<String, String> filter, boolean flag) {
		Account account = new Account();

		account.setName(flag ? filter.get("search") : filter.get("name"));
		account.setIpDomain(flag ? filter.get("search") : filter.get("ipDomain"));
		account.setCity(flag ? filter.get("search") : filter.get("city"));
		account.setState(flag ? filter.get("search") : filter.get("state"));
		account.setCountry(flag ? filter.get("search") : filter.get("country"));
		account.setType(flag ? filter.get("search") : filter.get("type"));
		account.setSalesforceId(flag ? filter.get("search") : filter.get("salesforceId"));
		return account;
	}

}
