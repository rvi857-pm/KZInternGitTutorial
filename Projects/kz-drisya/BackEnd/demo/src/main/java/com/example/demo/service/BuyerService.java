package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buyer;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BuyerRepository;

@Service
public class BuyerService {

	@Autowired
	AccountService service;
	
	@Autowired
	BuyerRepository buyerRepo;
	
	@Autowired 
	AccountRepository accRepo;
	

	@Autowired 
	CalaculationMetrics calculation;
	

	public List<Map<String,Object>> getBuyers( Map<String, String> Query, Integer page_no, Integer page_size) {
		
		String metrics = Query.get("metrics");
		String start = Query.get("start");
		String end = Query.get("end");
		
		Pageable paging = service.getPageUtils(page_no, page_size);
		String accountId = null;
		if( Query.get("account_id") != null)
			accountId = Query.get("account_id");
		else if( Query.get("account_name") != null)
			accountId = (accRepo.findAllByName(Query.get("account_name"))).getId();
		
		String[] metric = new String[0];
 		if ( metrics != null) {
			
			if ( metrics.equals("all"))
				metrics="score,buyer_count,marketing_qualified,persona_count,location_count";
	
			metric = metrics.split("[,]",0);	
		}
	
		
		Page<Buyer> pagedBuyers;
		if( accountId == null)
			pagedBuyers = buyerRepo.findAll(paging);
		else 
			pagedBuyers = buyerRepo.findAllByAccountId(accountId,paging);
		
		List<Buyer> buyers = new ArrayList<Buyer>();
		if ( pagedBuyers != null && pagedBuyers.hasContent())
			buyers = pagedBuyers.getContent();
		
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> calculated;
		
		for ( int itr = 0; itr < buyers.size(); itr++) {
			Buyer buyer = buyers.get(itr);
			Map<String, Object> mapBuyer = new HashMap<String , Object>();
			List<Buyer> buyerList = new ArrayList<Buyer>();
			buyerList.add(buyer);
			mapBuyer.put("id",buyer.getId());
			mapBuyer.put("account_id",buyer.getAccountId());
			mapBuyer.put("city",buyer.getCity());
			mapBuyer.put("state",buyer.getState());
			mapBuyer.put("country",buyer.getCountry());
			mapBuyer.put("source",buyer.getSource());
			mapBuyer.put("job_function",buyer.getJobFunction());
			mapBuyer.put("job_level",buyer.getJobLevel());
			mapBuyer.put("account_name",buyer.getAccount().getName());
			mapBuyer.put("activity",buyer.getActivities());
			
			calculated = calculation.compute(buyerList,start,end);
			for ( int i = 0; i < metric.length; i++) {
				
				String metricsKey= metric[i];
				if( calculated.containsKey(metricsKey) )
					mapBuyer.put(metricsKey, calculated.get(metricsKey));
			}
			
			result.add(mapBuyer);
		}
		
		return result;
	}
	
}
