package com.kwanzoo.project.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kwanzoo.project.model.Account;

@Repository
public interface Dao extends PagingAndSortingRepository<Account, Integer> {
	
	List<Account> findByCountry(String country, Pageable page);
	List<Account> findByName(String name, Pageable page);
	List<Account> findById(UUID id, Pageable page);
	List<Account> findByState(String state, Pageable page);
	List<Account> findByCity(String city, Pageable page);
	List<Account> findByDomain(String domain, Pageable page);
	List<Account> findByType(String type, Pageable page);
	List<Account> findByAccountId(String account_id, Pageable page);
	List<Account> findByCityAndStateAndCountry(String city, String state, String country, Pageable page);
	List<Account> findByStateOrCountry(String state, String country, Pageable page);
	
}
