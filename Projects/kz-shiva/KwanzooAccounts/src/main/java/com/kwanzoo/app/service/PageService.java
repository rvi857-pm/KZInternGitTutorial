package com.kwanzoo.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;

@Component
public class PageService {
	
	@Autowired
	private Utility utils;
	
	public Page<Account> execute(Map<String, String> filter){
		if( filter.get("search") != null ) {
			List<Account> list = utils.getList(filter);
			int page = Integer.parseInt(filter.get("page"));
			int size = Integer.parseInt(filter.get("page_size"));
			Pageable obj = PageRequest.of(page, size);
			return utils.pageUtility(obj, list);
		}else {
			int page = Integer.parseInt(filter.get("page"));
			int size = filter.get("page_size") != null ? Integer.parseInt(filter.get("page_size")) : 10;
			return utils.getPage(filter, page, size);
		}
	}
	
}
