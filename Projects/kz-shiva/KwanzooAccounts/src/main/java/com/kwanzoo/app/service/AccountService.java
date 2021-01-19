package com.kwanzoo.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.kwanzoo.app.model.Account;

@Component
public class AccountService {
	@Autowired
	private Utility utils;
	
	public List<Account> execute(Map<String, String> filter){
		return utils.getList(filter);
	}
}
