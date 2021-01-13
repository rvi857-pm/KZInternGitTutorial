package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;

@RestController
public class AccountController {

	@Autowired
	AccountRepository repo;
	
	@GetMapping("/")
	public String home(){
	
		return "home.jsp";
	}
	
	/*@GetMapping("/accounts")
	public  ModelAndView getAccounts( @RequestParam Map<String, String> query ) {
		
		List<Account> accounts;
		int page_no, page_size = 10;
		
		Pageable paging;
		if(!query.containsKey("page"))
			paging = null;
		else {
			page_no = Integer.parseInt(query.get("page"));
			if (query.containsKey("page_size"))
				page_size = Integer.parseInt(query.get("page_size"));
			paging = PageRequest.of(page_no - 1,page_size);
		}
			
		
		String city  = query.get("city");
		String country = query.get("country");
		String state = query.get("state");
		String name = query.get("name");
		Page<Account> pagedResult = repo.x(name, city,state,country,paging);
		accounts = pagedResult.toList();
		
		ModelAndView mv = new ModelAndView("show.jsp");
		mv.getModelMap().addAttribute("accounts", accounts);
		return mv;
	}*/
	
	@GetMapping("/accounts")
	public  List<Account> getAccounts( @RequestParam Map<String, String> query ) {
		
		int page_no, page_size = 10;
		
		Pageable paging;
		if(!query.containsKey("page"))
			paging = null;
		else {
			page_no = Integer.parseInt(query.get("page"));
			if (query.containsKey("page_size"))
				page_size = Integer.parseInt(query.get("page_size"));
			paging = PageRequest.of(page_no - 1,page_size);
		}
			
		
		String city  = query.get("city");
		String country = query.get("country");
		String state = query.get("state");
		String name = query.get("name");
		Page<Account> pagedResult = repo.x(name, city,state,country,paging);
			
		return pagedResult.toList();
		
	}
	
}
