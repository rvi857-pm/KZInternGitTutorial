package com.example.demo.controller;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;

import java.lang.Integer;
import java.util.List;

//controller module
@RestController
public class TestController {

//reference of userRepository	
	  @Autowired
	  private UserRepository userRepository;
	  
//setting the origin in-order to sync up with the front-end
//mapping to "/accounts route"
	@CrossOrigin(origins = "http://localhost:4200")  
	@GetMapping("/accounts")
	  public ResponseEntity<List<User>> getAllUsers(@RequestParam(name= "page",required = false) Integer pageNo, @RequestParam(name="page_size", required = false) Integer pageSize, @RequestParam(required = false) String name,@RequestParam(required = false) String ip_domain, @RequestParam(required = false) String city,@RequestParam(required = false) String state, @RequestParam(required = false) String country, @RequestParam(required = false) String type, @RequestParam(name="salesforce_id",required = false) String sfdc_id, @RequestParam(name="q",required = false)String keyword){   

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

 		//4 contrast cases with respect to page and page_size whether they are null or not
		if(pageNo == null && pageSize == null){	

			//storing the List<User> as return from search functions 
			List<User> newout = userRepository.findByU_Search(name, ip_domain ,sfdc_id,city, type, country, state, keyword);

			//returning the output as ResponseEntity
				 return new ResponseEntity<List<User>>(newout, new HttpHeaders() , HttpStatus.OK);	
		} 
		else if(pageNo != null && pageSize == null){
			pageSize = 10;
			Pageable paging = PageRequest.of(pageNo-1, pageSize);
		 		List<User> newout = userRepository.findByU_SearchP(name, ip_domain ,sfdc_id,city, type, country, state,keyword, paging);
				  return new ResponseEntity<List<User>>(newout, new HttpHeaders() , HttpStatus.OK);	
		}
		else if(pageNo == null && pageSize != null){
			pageNo= 0;
			Pageable paging = PageRequest.of(0, pageSize);
 		    List<User> newout = userRepository.findByU_SearchP(name, ip_domain ,sfdc_id,city, type, country, state,keyword, paging);
				  return new ResponseEntity<List<User>>(newout, new HttpHeaders() , HttpStatus.OK);	
		} else {
			    Pageable paging = PageRequest.of(pageNo-1, pageSize);
		 		List<User> newout = userRepository.findByU_SearchP(name, ip_domain ,sfdc_id,city, type, country, state,keyword, paging); 
		 		   return new ResponseEntity<List<User>>(newout, new HttpHeaders() , HttpStatus.OK);			
		}
	}	
}
		
		
		
		
		
		
