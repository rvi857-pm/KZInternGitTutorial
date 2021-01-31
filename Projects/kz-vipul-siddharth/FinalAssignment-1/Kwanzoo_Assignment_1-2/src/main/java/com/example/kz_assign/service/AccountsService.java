package com.example.kz_assign.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.kz_assign.dao.account_repo;
import com.example.kz_assign.models.account;
import com.example.kz_assign.models.buyer;

@Service
public class AccountsService {

	@Autowired
	private account_repo repo;
	
	public List<account> get_filteredaccounts(@RequestParam(defaultValue="",required=false)String page, 		//getting the optional parameters
											@RequestParam(defaultValue="",required=false)String page_size,
											@RequestParam(defaultValue="",required=false)String q,
											account probaccount,
											Model model) {
		ExampleMatcher QueryMatcher = ExampleMatcher.matchingAny()
			      .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("ip_domain", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("salesforce_id", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		
		ExampleMatcher StrictMatcher = ExampleMatcher.matchingAll()
			      .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("ip_domain", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
			      .withMatcher("salesforce_id", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
				
				
		Example<account> q_example = Example.of(probaccount, QueryMatcher);
		Example<account> strict_example = Example.of(probaccount, StrictMatcher );

	    List<account> accountslist = repo.findAll(strict_example);
	    if(!(q.equals(""))) {
	    	//System.out.println("check");
			account querymodel = new account(q,q,q,q,q,q,q);
			q_example = Example.of(querymodel, QueryMatcher);
			accountslist.retainAll(repo.findAll(q_example));
		}
	    
		return Custom_Pagination(page, page_size,accountslist);
	}

	
	private boolean defaultstringchecker(String sample) {
		return (sample.equals(""));
	}
	
	
	private List<account> Custom_Pagination(String page, String page_size, List<account> accountslist){ 
		int ps = 10;
		if(!(page_size.equals(""))) {							//checking if parameter given or not
			int check_ps = Integer.parseInt(page_size);			// checking for false values
			if(check_ps >0) {
				ps = check_ps;
			}
			else {
				return null;
			}
		}
		//
		//System.out.println(ps);
		if(page.equals("")) {									//Same as the above two steps (comments)
//			if(defaultstringchecker(page_size))
//				return accountslist;
//			else
				return Custom_Pagination("1",page_size,accountslist);
		}else {
			int pa = Integer.parseInt(page);
			if(pa>0) {
				if((pa-1)*ps >= accountslist.size()) {
					return null;
				}
				else if(pa*ps >=accountslist.size()) {
					return accountslist.subList((pa-1)*ps, (int) accountslist.size());
				}
				else {
					return accountslist.subList((pa-1)*ps, pa*ps);
				}
			}
				
			else
				return null;
		}
	}

	public List<buyer> getaccountbuyers(account probaccount){
		
		ExampleMatcher StrictMatcher = ExampleMatcher.matchingAll()
					.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("ip_domain", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
					.withMatcher("salesforce_id", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
				
//		System.out.println(a.getName()+ " "+ a.getSalesforce_id() + " "+ a.getCity());
		Example<account> strict_example = Example.of(probaccount, StrictMatcher );
		//System.out.println(strict_example);
		List<account> alist = repo.findAll(strict_example);
		List<buyer> buyerslist = new ArrayList<>();
		for(account acc:alist) {
			buyerslist.addAll(acc.getBuyers());
		}
		return buyerslist;
//		return null;
	}

}
