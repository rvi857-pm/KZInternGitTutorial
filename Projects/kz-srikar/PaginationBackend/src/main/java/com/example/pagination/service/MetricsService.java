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
	RedisCache redisCache;

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
			Map<String, Object> staticContentItem;
			Map<String, Object> dynamicContentItem;
			staticContentItem = redisCache.getStaticContentItem(account, account.getId());
			if (start == null && end == null) {
				dynamicContentItem = redisCache.getDynamicContentItem(account, account.getId());
			} else {
				dynamicContentItem = redisCache.getTimedContentItem(account, account.getId(), start, end);
			}

			staticContentItem.putAll(dynamicContentItem);

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
					contentItem.put("score", staticContentItem.get("score"));
					contentItem.put("marketing_qualified", staticContentItem.get("marketing_qualified"));
					contentItem.put("buyer_count", staticContentItem.get("buyer_count"));
					contentItem.put("activity_count", staticContentItem.get("activity_count"));
					contentItem.put("persona_count", staticContentItem.get("persona_count"));
					contentItem.put("location_count", staticContentItem.get("location_count"));
				} else {
					if (!contentItem.containsKey(metricParams.get(j))) {
						contentItem.put(metricParams.get(j), staticContentItem.get(metricParams.get(j)));
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
