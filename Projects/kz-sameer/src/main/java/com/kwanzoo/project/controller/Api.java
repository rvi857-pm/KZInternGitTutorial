package com.kwanzoo.project.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.model.AccountReturn;
import com.kwanzoo.project.model.ActivityReturn;
import com.kwanzoo.project.model.Buyer;
import com.kwanzoo.project.model.BuyerReturn;
import com.kwanzoo.project.model.PageInfo;
import com.kwanzoo.project.model.PagedReturn;
import com.kwanzoo.project.service.ActivityService;
import com.kwanzoo.project.service.BuyerService;
import com.kwanzoo.project.service.ProjectService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
public class Api {
	
	@Autowired
	ProjectService accountService;
	
	@Autowired
	BuyerService buyerService;
	
	@Autowired
	ActivityService activityService;
	
	@GetMapping("/accounts")
	public PagedReturn<AccountReturn> getAccountsByPage( @ModelAttribute Account account, @ModelAttribute PageInfo pageInfo){
		
		String cacheKey = account.toString() + pageInfo.toString();
		
		return accountService.findBy(account, pageInfo, cacheKey);	
	}
	
	@GetMapping("/buyers")
	public PagedReturn<BuyerReturn> getBuyers( @RequestParam(value = "account_name", required = false) String account_name,
										@RequestParam(value = "account_id", required = false) String account_id,
										@ModelAttribute Buyer buyer,
										@ModelAttribute PageInfo pageInfo){
		
		String cacheKey = account_id + account_name +  pageInfo.toString() + buyer.toString();
		
		return buyerService.findBuyers(buyer, account_name, account_id, pageInfo, cacheKey);	
	}
	
	@GetMapping("/activites")
	public PagedReturn<ActivityReturn> getBuyers( @RequestParam(value = "buyer_id", required = false) String buyer_id,
									@RequestParam(value = "page", required = false, defaultValue = "0") int page,
									@RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize){
		
		String cacheKey = buyer_id + Integer.toString(page) + Integer.toString(pageSize);
		
		return activityService.findActivities(buyer_id, page, pageSize, cacheKey);	
	}

	@CrossOrigin()
	@PostMapping("/file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
        	return "file is empty";
        } else {

            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(reader)
                        .withType(Account.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                List<Account> users = csvToBean.parse();
                
                for(Account user : users) {
                	
                	user.setId(UUID.randomUUID().toString());
                	
                }
                
                accountService.addAccounts(users);
 
                return "Accounts added successfully";

            } catch (Exception ex) {
            	return "Please ensure your csv file has valid format";
            }
        }
    }
	
}
