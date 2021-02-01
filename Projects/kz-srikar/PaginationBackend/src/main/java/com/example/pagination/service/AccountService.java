package com.example.pagination.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pagination.constants.Constants;
import com.example.pagination.dao.AccountRepository;
import com.example.pagination.model.Account;
import com.example.pagination.model.PageResponse;


@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MetricsCalculation metricsCalculation;

	public Page<Account> accountServiceUtility(Account account, Integer page, Integer pageSize, String q) {
		if (page == null) {
			List<Account> allResults = accountRepository.findAll();
			Pageable pageableInstance = PageRequest.of(0, allResults.size());
			return pageUtility(pageableInstance, allResults);
		}

		if (pageSize == null) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;
		}

		if (q != null) {
			if (account.getName() == null && account.getIpDomain() == null && account.getCity() == null
					&& account.getState() == null && account.getCountry() == null && account.getType() == null
					&& account.getSalesforceId() == null) {
				return getUniversalSearchResults(page, pageSize, q);
			}
			return getMultiSearchResults(page, pageSize, account, q);
		}

		return getCompoundSearchResults(page, pageSize, account);
	}

	public PageResponse metricsUtility(Page<Account> accountPage, List<String> metricParams, List<String> exclude,
			String start, String end) {

		if (metricParams == null) {
			metricParams = Collections.emptyList();
		}

		if (exclude == null) {
			exclude = Collections.emptyList();
		}

		List<Account> accounts = accountPage.getContent();

		PageResponse response = new PageResponse();
		response.setTotalElements((int) accountPage.getTotalElements());

		List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < accounts.size(); i++) {

			Account account = accounts.get(i);
			Map<String, Object> returnedContentItem = metricsCalculation.getContentItem(account, account.getId(), start,
					end);
			Map<String, Object> contentItem = new HashMap<>();

			contentItem.put("id", account.getId());
			contentItem.put("name", account.getName());
			contentItem.put("ip_domain", account.getIpDomain());
			contentItem.put("city", account.getCity());
			contentItem.put("state", account.getState());
			contentItem.put("country", account.getCountry());
			contentItem.put("type", account.getType());
			contentItem.put("salesforce_id", account.getSalesforceId());

			for (int j = 0; j < metricParams.size(); j++) {
				if (metricParams.get(j).equals("all")) {
					contentItem.put("score", returnedContentItem.get("score"));
					contentItem.put("marketing_qualified", returnedContentItem.get("marketing_qualified"));
					contentItem.put("buyer_count", returnedContentItem.get("buyer_count"));
					contentItem.put("activity_count", returnedContentItem.get("activity_count"));
					contentItem.put("persona_count", returnedContentItem.get("persona_count"));
					contentItem.put("location_count", returnedContentItem.get("location_count"));
				} else {
					if (!contentItem.containsKey(metricParams.get(j))) {
						contentItem.put(metricParams.get(j), returnedContentItem.get(metricParams.get(j)));
					}

				}
			}

			for (int j = 0; j < exclude.size(); j++) {
				if (contentItem.containsKey(exclude.get(j))) {
					contentItem.remove(exclude.get(j));
				}
			}

			content.add(contentItem);
		}

		response.setContent(content);

		return response;
	}

	public Page<Account> getUniversalSearchResults(int page, int pageSize, String q) {
		Account account = new Account();
		account.setAll(q);
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		Example<Account> universalSearchExample = Example.of(account,
				generateExampleMatcher(ExampleMatcher.matchingAny()));
		return accountRepository.findAll(universalSearchExample, pageableInstance);
	}

	public Page<Account> getCompoundSearchResults(int page, int pageSize, Account account) {
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		Example<Account> compoundSearchExample = Example.of(account,
				generateExampleMatcher(ExampleMatcher.matchingAll()));
		return accountRepository.findAll(compoundSearchExample, pageableInstance);
	}

	public Page<Account> getMultiSearchResults(int page, int pageSize, Account accountCompound, String q) {
		Account accountUniversal = new Account();
		accountUniversal.setAll(q);
		Example<Account> universalSearchExample = Example.of(accountUniversal,
				generateExampleMatcher(ExampleMatcher.matchingAny()));
		List<Account> universalAccountList = accountRepository.findAll(universalSearchExample);

		Example<Account> compoundSearchExample = Example.of(accountCompound,
				generateExampleMatcher(ExampleMatcher.matchingAll()));
		List<Account> compoundAccountList = accountRepository.findAll(compoundSearchExample);

		List<Account> multiSearchList = universalAccountList.stream().distinct().filter(compoundAccountList::contains)
				.collect(Collectors.toList());
		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		return pageUtility(pageableInstance, multiSearchList);
	}

	// https://stackoverflow.com/questions/37136679/how-to-convert-a-list-of-enity-object-to-page-object-in-spring-mvc-jpa/46765495
	private Page<Account> pageUtility(Pageable obj, List<Account> list) {
		int total = list.size();
		int start = (int) obj.getOffset();
		int end = Math.min((start + obj.getPageSize()), total);

		List<Account> output = new ArrayList<>();
		if (start <= end)
			output = list.subList(start, end);

		return new PageImpl<Account>(output, obj, total);
	}

	private ExampleMatcher generateExampleMatcher(ExampleMatcher searchMatcher) {
		return searchMatcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("ipDomain", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("city", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("state", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("country", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains())
				.withMatcher("salesforceId", ExampleMatcher.GenericPropertyMatchers.contains());
	}
}
