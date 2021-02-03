package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.model.Buyer;

@Service
public class MetricService {

	@Autowired 
	CalaculationMetrics calculation;
	
	private Map<String, Object> getAccountDetails ( Account account, String[] excludes){
		Map<String, Object> mapAccount = new HashMap<String , Object>();
		
		
		mapAccount.put( "id", account.getId());
		mapAccount.put( "name", account.getName());
		mapAccount.put( "ip_domain", account.getIpDomain());
		mapAccount.put( "city", account.getCity());
		mapAccount.put( "state", account.getState());
		mapAccount.put( "country", account.getCountry());
		mapAccount.put( "type", account.getType());
		mapAccount.put( "salesforce_id", account.getSalesforceId());
		
		for ( int i = 0; i < excludes.length; i++) {
			String prop = excludes[i];
			if ( mapAccount.containsKey(prop)) {
				mapAccount.remove(prop);
			}
		}
		return mapAccount;
	}
	
	public List<Map<String,Object>> metricsFilter ( Page<Account> accounts, String metrics, String start, String end, String exclude) {
		
		/**
		 * Initializations
		 */

		String[] metric = new String[0];
		String[] excludes = new String[0];
		
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> calculated;
		if ( metrics != null) {
			
			if ( metrics.equals("all"))
				metrics="score,buyer_count,marketing_qualified,persona_count,location_count,activity_count";
	
			metric = metrics.split("[,]",0);	
		}
			
		if( exclude != null)
			excludes = exclude.split("[,]",0);
		
		/**
		 * Calculations
		 */
	
		List<Account> liaccounts = accounts.getContent();
		
		for ( int i = 0; i < liaccounts.size(); i++) {
			
			Account account = liaccounts.get(i);
			Map<String, Object> accountObject = new HashMap<String, Object>();
			String id = account.getId();
			
			accountObject.putAll( getAccountDetails( account, excludes));
			calculated = calculation.getComputed(account,id,start,end);
			
			for ( int itr = 0; itr < metric.length; itr++) {
				
				String metricsKey= metric[itr];
				if( calculated.containsKey(metricsKey) )
					accountObject.put(metricsKey, calculated.get(metricsKey));
			}
			result.add(accountObject);
		}
		return result;
	}
}
