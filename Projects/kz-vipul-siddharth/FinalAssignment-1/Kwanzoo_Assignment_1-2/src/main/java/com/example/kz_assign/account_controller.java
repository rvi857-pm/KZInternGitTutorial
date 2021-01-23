package com.example.kz_assign;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kz_assign.models.account;
import com.example.kz_assign.service.AccountsService;
import com.example.kz_assign.service.MetricsService;

@RestController
public class account_controller {
	
	@Autowired
	private AccountsService accservice;
	
	@Autowired
	private MetricsService metricsservice;
	
	List<account> accountslist;
	
	@GetMapping("/accounts")		//The mapping for accounts view
	public List<Map<String,Object>> show_accountlist(@RequestParam(defaultValue="",required=false)String page, 		//getting the optional parameters
											@RequestParam(defaultValue="",required=false)String page_size,
											@RequestParam(defaultValue="",required=false)String q,
											@RequestParam(defaultValue="",required=false)String metric,
											account probaccount,
											Model model) {
		accountslist = accservice.get_filteredaccounts(page, page_size, q, probaccount, model);
		List<Map<String,Object>> datamaplist = metricsservice.addmetrics(accountslist, metric);
		return datamaplist;
	}

//	@GetMapping("/temp")
//	public List<Map<String,Object>> show_buyeraccount() {
//		List<Map<String,Object>> datamaplist = metricsservice.addmetrics(accountslist, "score"); 
//		
//		return datamaplist;
//	}
}