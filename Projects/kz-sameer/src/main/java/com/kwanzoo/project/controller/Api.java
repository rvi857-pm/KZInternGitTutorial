package com.kwanzoo.project.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kwanzoo.project.model.Account;
import com.kwanzoo.project.model.AccountReturn;
import com.kwanzoo.project.model.Activity;
import com.kwanzoo.project.model.Buyer;
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
	public Page<Buyer> getBuyers( @ModelAttribute Buyer buyer, @ModelAttribute PageInfo pageInfo){
		
		String cacheKey = buyer.toString() + pageInfo.toString();
		
		return buyerService.findBuyerBy(buyer, pageInfo, cacheKey);	
	}
	
	@GetMapping("/activites")
	public Page<Activity> getBuyers( @ModelAttribute Activity activity, @ModelAttribute PageInfo pageInfo){
		
		String cacheKey = activity.toString() + pageInfo.toString();
		
		return activityService.findActivityBy(activity, pageInfo, cacheKey);	
	}


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
 
                return "ok";

            } catch (Exception ex) {
            	return "Error Occured";
            }
        }
    }
	
}
