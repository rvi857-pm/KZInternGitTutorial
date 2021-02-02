package com.example.pagination.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pagination.constants.Constants;
import com.example.pagination.dao.BuyerRepository;
import com.example.pagination.model.Account;
import com.example.pagination.model.Buyer;
import com.example.pagination.model.PageResponse;
import com.example.pagination.utility.PaginationHelper;

@Service
public class BuyerService {

	@Autowired
	private BuyerRepository buyerRepository;

	@Autowired
	private MetricsCalculation metricsCalculation;

	public Page<Buyer> buyerServiceUtility(Account account, Integer page, Integer pageSize) {
		List<Buyer> allResults = buyerRepository.findByAccount(account);
		PaginationHelper<Buyer> ph = new PaginationHelper<Buyer>();
		if (page == null) {
			Pageable pageableInstance = PageRequest.of(0, allResults.size());
			return ph.pageUtility(pageableInstance, allResults);
		}
		if (pageSize == null) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;
		}

		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		return ph.pageUtility(pageableInstance, allResults);
	}

	public PageResponse metricsUtility(Page<Buyer> buyerPage, List<String> metricParams, List<String> exclude,
			String start, String end) {

		if (metricParams == null) {
			metricParams = Collections.emptyList();
		}

		if (exclude == null) {
			exclude = Collections.emptyList();
		}

		List<Buyer> buyers = buyerPage.getContent();

		PageResponse response = new PageResponse();
		response.setTotalElements((int) buyerPage.getTotalElements());

		List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < buyers.size(); i++) {

			Buyer buyer = buyers.get(i);
			Map<String, Object> returnedContentItem = metricsCalculation.getBuyerContentItem(buyer, buyer.getId(),
					start, end);
			Map<String, Object> contentItem = new HashMap<>();

			contentItem.put("id", buyer.getId());
			contentItem.put("city", buyer.getCity());
			contentItem.put("state", buyer.getState());
			contentItem.put("country", buyer.getCountry());
			contentItem.put("source", buyer.getSource());
			contentItem.put("job_level", buyer.getJobLevel());
			contentItem.put("job_function", buyer.getJobFunction());

			for (int j = 0; j < metricParams.size(); j++) {
				if (metricParams.get(j).equals("all")) {
					contentItem.put("score", returnedContentItem.get("score"));
					contentItem.put("marketing_qualified", returnedContentItem.get("marketing_qualified"));
					contentItem.put("activity_count", returnedContentItem.get("activity_count"));
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
