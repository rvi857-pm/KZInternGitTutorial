package com.example.kz_assign;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.kz_assign.dao.account_repo;
import com.example.kz_assign.dao.buyer_repo;
import com.example.kz_assign.models.account;
import com.example.kz_assign.models.buyer;

@RestController
public class account_controller {

	@Autowired
	private account_repo repo;
	
	@Autowired
	private buyer_repo br;
	
	private String lastpage;
	
	@GetMapping("/accounts")		//The mapping for accounts view
	//@ResponseBody
	public List<account> show_accountlist(@RequestParam(defaultValue="",required=false)String page, 		//getting the optional parameters
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
			account querymodel = new account();
			querymodel.setName(q);
			querymodel.setCity(q);
			querymodel.setCountry(q);
			querymodel.setState(q);
			querymodel.setIp_domain(q);
			querymodel.setIp_domain(q);
			querymodel.setSalesforce_id(q);
			querymodel.setType(q);
			q_example = Example.of(querymodel, QueryMatcher);
			accountslist.retainAll(repo.findAll(q_example));
		}
	    
		return Pagination(page, page_size,accountslist);
	}

	
	private boolean defaultstringchecker(String sample) {
		return (sample.equals(""));
	}
	
	
	private List<account> Pagination(String page, String page_size, List<account> accountslist){ 
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
			if(defaultstringchecker(page_size))
				return accountslist;
			else
				return Pagination("1",page_size,accountslist);
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

//	@GetMapping("/temp")
//	public List<buyer> show_buyeraccount(account a) {
//		return br.findAll();
//	}
}