package com.kwanzoo.app.Utility;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.model.Buyer;

@Component
public class Probe {

	public Account getAccountProbe(Map<String, String> filter, boolean flag) {
		Account account = new Account();

		account.setId(filter.get("id"));
		account.setName(flag ? filter.get("search") : filter.get("name"));
		account.setIp_domain(flag ? filter.get("search") : filter.get("ip_domain"));
		account.setCity(flag ? filter.get("search") : filter.get("city"));
		account.setState(flag ? filter.get("search") : filter.get("state"));
		account.setCountry(flag ? filter.get("search") : filter.get("country"));
		account.setType(flag ? filter.get("search") : filter.get("type"));
		account.setSalesforce_id(flag ? filter.get("search") : filter.get("salesforce_id"));
		return account;
	}
	
	public Buyer getBuyerProbe(Map<String, String> filter) {
		Buyer buyer = new Buyer();
		
		buyer.setId(filter.get("buyer_id"));
//		buyer.setCity(flag ? filter.get("search") : filter.get("city"));
//		buyer.setState(flag ? filter.get("search") : filter.get("state"));
//		buyer.setCountry(flag ? filter.get("search") : filter.get("country"));
//		buyer.setSource(flag ? filter.get("search") : filter.get("source"));
//		buyer.setState(flag ? filter.get("search") : filter.get("job_level"));
//		buyer.setState(flag ? filter.get("search") : filter.get("job_function"));
		
		return buyer;
	}

}
