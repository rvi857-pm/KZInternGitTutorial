package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Buyer;


public interface BuyerRepository extends JpaRepository<Buyer, String> {

	//@Query(" select  (city,state,country) as location, count(id) from Buyer groub by city, state, country having buyer.accountId = :Id")
	//Object findLocationCount( String Id);
	
	//@Query ("SELECT jobLevel, count(id) FROM :buyers GROUP BY jobLevel")
	//Object findPersonCount( @Param("Id") String Id, @Param("buyers") Object buyers);

	Page<Buyer> findAllByAccountId(String account_id, Pageable paging);
}
