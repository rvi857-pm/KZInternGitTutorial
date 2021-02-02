package com.example.pagination.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pagination.constants.Constants;
import com.example.pagination.dao.ActivityRepository;
import com.example.pagination.model.Buyer;
import com.example.pagination.utility.PaginationHelper;
import com.example.pagination.model.Activity;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	public Page<Activity> activityServiceUtility(Buyer buyer, Integer page, Integer pageSize) {
		List<Activity> allResults = activityRepository.findByBuyer(buyer);
		PaginationHelper<Activity> ph = new PaginationHelper<Activity>();

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

}
