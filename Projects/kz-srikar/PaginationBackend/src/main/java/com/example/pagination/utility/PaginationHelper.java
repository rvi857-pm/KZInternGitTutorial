package com.example.pagination.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PaginationHelper<T> {
	// https://stackoverflow.com/questions/37136679/how-to-convert-a-list-of-enity-object-to-page-object-in-spring-mvc-jpa/46765495
	public Page<T> pageUtility(Pageable obj, List<T> list) {
		int total = list.size();
		int start = (int) obj.getOffset();
		int end = Math.min((start + obj.getPageSize()), total);

		List<T> output = new ArrayList<>();
		if (start <= end)
			output = list.subList(start, end);

		return new PageImpl<T>(output, obj, total);
	}
}
