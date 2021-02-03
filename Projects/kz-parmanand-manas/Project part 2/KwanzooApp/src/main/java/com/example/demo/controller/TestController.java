package com.example.demo.controller;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ActivityRepository;
import com.example.demo.dao.BuyerRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.Account;
import com.example.demo.model.Activity;
import com.example.demo.model.Buyer;
import com.example.demo.model.ResponseList;
import com.example.demo.services.CalculationAndMappify;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.Integer;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

//controller module
@RestController
public class TestController {

//reference of userRepository	
	  @Autowired
	  private UserRepository userRepository;
	  
	  @Autowired
	  private BuyerRepository BuyerRepo;
	  
	  @Autowired
	  private ActivityRepository ActivityRepo;
	  
	  @Autowired
	  private CalculationAndMappify calculationAndMappify;
	  
//setting the origin in-order to sync up with the front-end
//mapping to "/accounts route"
	@GetMapping("/accounts")
<<<<<<< HEAD
	  public ResponseList getAllUsers(@RequestParam(name= "page",required = false) Integer pageNo, @RequestParam(name="page_size", required = false) Integer pageSize, @RequestParam(required = false) String name,@RequestParam(required = false) String ip_domain, @RequestParam(required = false) String city,@RequestParam(required = false) String state, @RequestParam(required = false) String country, @RequestParam(required = false) String type, @RequestParam(name="salesforce_id",required = false) String sfdc_id, @RequestParam(name="q",required = false)String keyword , @RequestParam(name="metrics",required = false)List<String>metrics, @RequestParam(name="exclude",required = false)List<String>exclude, @RequestParam(name="start",required = false)String start, @RequestParam(name="end",required = false)String end ) throws ParseException{   
=======
//	@Cacheable(key="#name", value="account" , condition="#name != null" )
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseList getAllUsers(@RequestParam(name= "page",required = false) Integer pageNo, @RequestParam(name="page_size", required = false) Integer pageSize, @RequestParam(required = false) String name,@RequestParam(required = false) String ip_domain, @RequestParam(required = false) String city,@RequestParam(required = false) String state, @RequestParam(required = false) String country, @RequestParam(required = false) String type, @RequestParam(name="salesforce_id",required = false) String sfdc_id, @RequestParam(name="q",required = false)String keyword , @RequestParam(name="metrics",required = false)List<String>metrics, @RequestParam(name="exclude",required = false)List<String>exclude, @RequestParam(name="start",required = false)String start, @RequestParam(name="end",required = false)String end ) throws ParseException{
>>>>>>> 41bfb6f009f53b6d7ec145dad105567e9fb9fe71

		//avoiding null values, just by setting it to ""(empty string)
		if(keyword == null) {
	    	keyword = "";
	    }
		if(name == null){
			name = "";
		}
 		if(city == null) {
 			city = "";
 		}
 		if(state == null) {
 			state = "";
 		}
 		if(country == null){
 			country = "";
 		}
 		if(type == null) {
 			type = "";
 		}
 		if(sfdc_id == null) {
 			sfdc_id = "";
 		}
 		if(ip_domain == null) {
 			ip_domain = "";
 		}
 		if(metrics == null) {
 			metrics = Collections.emptyList();
 		}
 		if(exclude == null) {
 			exclude = Collections.emptyList();
 		}
 		if(start == null) {
 			start = "";
 		}
 		if(end == null) {
 			end = "";
 		}

 		//4 contrast cases with respect to page and page_size whether they are null or not
		if(pageNo == null && pageSize == null){	
			//storing the List<User> as return from search functions 
		    List<Account>transit =  userRepository.findByU_Search(name, ip_domain ,sfdc_id,city, type, country, state, keyword);
            ResponseList response = calculationAndMappify.calAndMappify(transit, metrics, exclude,start, end) ;
            return response;
			//returning the output as ResponseEntity
		} 
		else if(pageNo != null && pageSize == null){
			pageSize = 10;
			pageNo--;	
		}
		else if(pageNo == null && pageSize != null){
			pageNo = 0;	
		} 
		else{
			   pageNo--;
		}
		Pageable paging = PageRequest.of(pageNo, pageSize);
		List<Account>transit = userRepository.findByU_SearchP(name, ip_domain ,sfdc_id,city, type, country, state,keyword, paging);
		ResponseList response = calculationAndMappify.calAndMappify(transit, metrics, exclude,start, end) ;
		return response;
	}
	@GetMapping("/buyers")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseList getAllbuyers(@RequestParam(name= "page",required = false) Integer pageNo, @RequestParam(name="page_size", required = false) Integer pageSize, @RequestParam(name= "account_name",required = false) String account_name, @RequestParam(name= "account_id",required = false) String account_id,  @RequestParam(name="metrics",required = false)List<String>metrics, @RequestParam(name="exclude",required = false)List<String>exclude, @RequestParam(name="start",required = false)String start, @RequestParam(name="end",required = false)String end) throws ParseException {
		if(metrics == null) {
 			metrics = Collections.emptyList();
 		}
 		if(exclude == null) {
 			exclude = Collections.emptyList();
 		}
 		if(start == null) {
 			start = "";
 		}
 		if(end == null) {
 			end = "";
 		}
 		if(account_name == null) {
 			account_name = "";
 		}
 		if(account_id == null) {
 			account_id = "";
 		}
		
		if(pageNo == null && pageSize == null) {
			if(account_id.equals("")) {
			List<Buyer>transit1 = BuyerRepo.findAll_WP();
			ResponseList response = calculationAndMappify.calAndMappify_Buyer(transit1, metrics, exclude, start, end);
			return response;
			}
			else {
				List<Buyer>transit1 = BuyerRepo.findByAccountId_WP(account_id);
				ResponseList response = calculationAndMappify.calAndMappify_Buyer(transit1, metrics, exclude, start, end );
				return response;
			}
		}
		else if(pageNo != null && pageSize == null) {
			pageSize = 10;
			pageNo--;
		}
		else if(pageNo == null && pageSize != null) {
			pageNo = 0;
		}
		else {
			pageNo--;
		}
		Pageable paging = PageRequest.of(pageNo, pageSize);
		if(account_id.equals("")) {
			List<Buyer>transit1 = BuyerRepo.findAll_P(paging);
			ResponseList response = calculationAndMappify.calAndMappify_Buyer(transit1, metrics, exclude, start, end );
			return response;
		}
		
		List<Buyer>transit1 = BuyerRepo.findByAccountId_P(account_id,paging);
		ResponseList response = calculationAndMappify.calAndMappify_Buyer(transit1, metrics, exclude, start, end );
		return response;
	}
	
	@GetMapping("/activities")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseList getAllactivities(@RequestParam(name= "page",required = false) Integer pageNo, @RequestParam(name="page_size", required = false) Integer pageSize,@RequestParam(name="buyer_id", required = false) String buyer_id) {
		if(buyer_id == null) {
			buyer_id = "";
		}
		
		if(pageNo == null && pageSize == null) {
			if(buyer_id.equals("")) {
				List<Activity>transit1 = ActivityRepo.findAll_WP();
				ResponseList response = calculationAndMappify.calAndMappify_Activity(transit1);
				return response;	
			}
			else {
				List<Activity>transit1 = ActivityRepo.findById_WP(buyer_id);
				ResponseList response = calculationAndMappify.calAndMappify_Activity(transit1);
				return response;		
			}
		}
		else if(pageNo != null && pageSize == null) {
			pageSize = 10;
			pageNo--;
		}
		else if(pageNo == null && pageSize != null) {
			pageNo = 0;
		}
		else {
			pageNo--;
		}
		
		Pageable paging = PageRequest.of(pageNo, pageSize);
		if(buyer_id.equals("")) {
			List<Activity>transit1 = ActivityRepo.findAll_P(paging);
			ResponseList response = calculationAndMappify.calAndMappify_Activity(transit1);
			return response;
		}
		
		List<Activity>transit1 = ActivityRepo.findById_P(buyer_id,paging);
		ResponseList response = calculationAndMappify.calAndMappify_Activity(transit1);
		return response;
		
	}
	
	@PostMapping("/uploads")
	@CrossOrigin(origins = "http://localhost:4200")
	public String uploadCSVFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
        	return "file is empty";
        } 
        else {
              try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(reader)
                        .withType(Account.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Account> accounts = csvToBean.parse();
                
                for(Account account : accounts) {
                	
                	account.setId(UUID.randomUUID().toString());
                	
                }
                
                userRepository.saveAll(accounts);
 
                return "Accounts have been added successfully";

            } catch (Exception ex) {
            	System.out.println(ex.getMessage());
            	return "CSV file has invalid format";
            }
        }
    }	
}
		
		
		
		
		
		
