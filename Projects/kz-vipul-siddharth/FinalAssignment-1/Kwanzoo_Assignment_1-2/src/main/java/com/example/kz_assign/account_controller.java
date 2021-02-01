package com.example.kz_assign;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.kz_assign.models.account;
import com.example.kz_assign.models.activity;
import com.example.kz_assign.models.buyer;
import com.example.kz_assign.service.AccountsService;
import com.example.kz_assign.service.BuyerService;
import com.example.kz_assign.service.CSVService;
import com.example.kz_assign.service.MetricsService;
import com.example.kz_assign.utilities.CSVHelper;

@RestController
public class account_controller {
	
	@Autowired
	private AccountsService accservice;
	
	@Autowired
	private MetricsService metricsservice;
	
	@Autowired
	private BuyerService buyerservice;
	
	@Autowired
	  CSVService fileService;
	
	List<account> accountslist;
	
	@GetMapping("/accounts")		//The mapping for accounts view
	public List<Map<String,Object>> show_accountlist(@RequestParam(defaultValue="",required=false)String page, 		//getting the optional parameters
											@RequestParam(defaultValue="",required=false)String page_size,
											@RequestParam(defaultValue="",required=false)String q,
											@RequestParam(defaultValue="",required=false)List<String> metric,
											@RequestParam(defaultValue="",required=false)String startdate,
											@RequestParam(defaultValue="",required=false)String enddate,
											@RequestParam(defaultValue="",required=false)List<String> exclude,
											account probaccount,
											Model model) {
		accountslist = accservice.get_filteredaccounts(page, page_size, q, probaccount, model);
		if(accountslist!=null) {
			List<Map<String,Object>> datamaplist = metricsservice.addmetrics(accountslist, metric, startdate, enddate, exclude);
			return datamaplist;
		}
		return new ArrayList<>();
	}

	@GetMapping("/buyers")
	public List<Map<String,Object>> getallbuyers(@RequestParam(defaultValue="",required=false)List<String> exclude,
											@RequestParam(defaultValue="",required=false)List<String> metrics,
											account probaccount){
		List<buyer> buyerslist;
		List<Map<String,Object>> buyerdatamap;
		if(probaccount!=null) {
			buyerslist = accservice.getaccountbuyers(probaccount);
			buyerdatamap = buyerservice.tomaplist( buyerslist, exclude);
		}else {
			buyerslist = buyerservice.getallbuyerslist();
			buyerdatamap = buyerservice.tomaplist(buyerslist, exclude);
		}
		if(metrics.size()>0) {
			metricsservice.getbuyermetrics(buyerslist, buyerdatamap, metrics);
		}
		return buyerdatamap;
	}
	
	@GetMapping("/activities")
	public Set<activity> getbuyeractivities(@RequestParam(defaultValue="",required=true)String buyer_id){
		return buyerservice.getbuyeractivities(buyer_id);
	}
	
	
	@RequestMapping(path="/uploadaccountlist",method=RequestMethod.POST,consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	      try {
	    	  fileService.save(file);
	        message = "Uploaded the file successfully";
	
	        return message;
	      } catch (Exception e) {
	        message = "Could not upload the file";
	        return message;
	      }
    }
}