package com.example.pagination.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import com.example.pagination.model.Account;
import com.example.pagination.model.PageResponse;

@Service
public class MetricsService {

	@Autowired
	MetricsCalculation metricsCalculation;

	public PageResponse metricsServiceUtility(Page<Account> accountPage, List<String> metricParams,
			List<String> exclude, String start, String end) {

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
}
