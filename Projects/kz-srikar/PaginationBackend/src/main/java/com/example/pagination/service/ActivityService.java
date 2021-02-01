package com.example.pagination.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.pagination.constants.Constants;
import com.example.pagination.dao.ActivityRepository;
import com.example.pagination.model.Buyer;
import com.example.pagination.model.Activity;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	public Page<Activity> activityServiceUtility(Buyer buyer, Integer page, Integer pageSize) {
		List<Activity> allResults = activityRepository.findByBuyer(buyer);
		if (page == null) {
			Pageable pageableInstance = PageRequest.of(0, allResults.size());
			return pageUtility(pageableInstance, allResults);
		}
		if (pageSize == null) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;
		}

		Pageable pageableInstance = PageRequest.of(page - 1, pageSize);
		return pageUtility(pageableInstance, allResults);
	}

	// https://stackoverflow.com/questions/37136679/how-to-convert-a-list-of-enity-object-to-page-object-in-spring-mvc-jpa/46765495
	private Page<Activity> pageUtility(Pageable obj, List<Activity> list) {
		int total = list.size();
		int start = (int) obj.getOffset();
		int end = Math.min((start + obj.getPageSize()), total);

		List<Activity> output = new ArrayList<>();
		if (start <= end)
			output = list.subList(start, end);

		return new PageImpl<Activity>(output, obj, total);
	}
}
