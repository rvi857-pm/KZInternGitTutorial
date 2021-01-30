package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Buyer;


public interface BuyerRepository extends JpaRepository<Buyer, String> {

	//@Query(" select  (city,state,country) as location, count(id) from Buyer groub by city, state, country having buyer.accountId = :Id")
	//Object findLocationCount( String Id);
	
	//@Query ("SELECT jobLevel, count(id) FROM :buyers GROUP BY jobLevel")
	//Object findPersonCount( @Param("Id") String Id, @Param("buyers") Object buyers);
	
}
