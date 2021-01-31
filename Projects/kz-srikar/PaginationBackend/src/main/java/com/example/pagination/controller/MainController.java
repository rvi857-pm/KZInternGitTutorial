package com.example.pagination.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import com.example.pagination.dao.AccountRepository;
import com.example.pagination.dao.ActivityRepository;
import com.example.pagination.dao.BuyerRepository;
import com.example.pagination.model.Account;
import com.example.pagination.model.Activity;
import com.example.pagination.model.Buyer;
import com.example.pagination.model.PageResponse;
import com.example.pagination.service.AccountService;
import com.example.pagination.service.MetricsService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@RestController
public class MainController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private BuyerRepository buyerRepo;
	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private MetricsService metricsService;

	@GetMapping(path = "/accounts")
	public PageResponse search(@ModelAttribute Account account, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String q,
			@RequestParam(required = false) List<String> metrics, @RequestParam(required = false) List<String> exclude,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end) {

		Page<Account> searchAccounts = accountService.accountServiceUtility(account, page, pageSize, q);

		return metricsService.metricsServiceUtility(searchAccounts, metrics, exclude, start, end);
	}

	@PostMapping(path = "/upload-accounts-csv")
	public void uploadAccount(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return;
		} else {

			// parse CSV file to create a list of `User` objects
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

				// create csv bean reader
				CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(reader).withType(Account.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				// convert `CsvToBean` object to list of users
				List<Account> accounts = csvToBean.parse();

				for (int i = 0; i < accounts.size(); i++) {
					String uuid = UUID.randomUUID().toString();
					accounts.get(i).setId(uuid);
				}
				accountRepo.saveAll(accounts);
			} catch (Exception ex) {
				return;
			}
		}

	}

	@GetMapping(path = "/buyers")
	public List<Buyer> buyers(@ModelAttribute Account account) {
		return buyerRepo.findByAccount(account);
	}

	@GetMapping(path = "/activities")
	public List<Activity> activities(@ModelAttribute Buyer buyer) {
		return activityRepo.findByBuyer(buyer);
	}

}