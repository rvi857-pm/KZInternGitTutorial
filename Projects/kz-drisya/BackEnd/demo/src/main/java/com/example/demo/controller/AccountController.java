package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Account;
import com.example.demo.model.Activity;
import com.example.demo.service.AccountService;
import com.example.demo.service.ActivityService;
import com.example.demo.service.BuyerService;
import com.example.demo.service.FileService;
import com.example.demo.service.MetricService;


@RestController
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	MetricService metricService;
	
	@Autowired
	BuyerService buyerService;
	
	@Autowired
	ActivityService actService;
	
	@Autowired
	FileService fileService;
	
	@GetMapping("/")
	public String home(){
	
		return "home.jsp";
	}

	
	@GetMapping("/accounts")
	public List<Map<String,Object>> getAccounts( @ModelAttribute Account account, @RequestParam (required = false) Integer page, 
			@RequestParam (required = false) Integer page_size, @RequestParam (required = false) String q, @RequestParam ( required = false) String metrics, @RequestParam ( required = false) String start, @RequestParam ( required = false) String end, @RequestParam ( required = false) String exclude) {
		Page <Account> accounts= accountService.getAllAccounts( account, page, page_size, q);
		return metricService.metricsFilter(accounts, metrics,start, end,exclude);
			
	}
	
	@PostMapping("/accounts")
	public String fileupload( @RequestParam("file") MultipartFile multipartfile) {
		
		return fileService.uploadFile( multipartfile);	
	}
	
	@GetMapping("/buyer")
	public List<Map<String,Object>> getBuyers(@RequestParam Map<String, String> Query, @RequestParam (required = false) Integer page, 
			@RequestParam (required = false) Integer page_size){
	
		
		return buyerService.getBuyers(Query, page, page_size);
	}
	
	@GetMapping("/activity")
	public Page<Activity> getActivities(@RequestParam Map<String, String> Query, @RequestParam (required = false) Integer page, @RequestParam (required = false) Integer page_size){
		
		return actService.getActivity(Query, page, page_size);
	}
	
	
	
	
}
