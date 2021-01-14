package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Account;


public interface AccountRepository extends JpaRepository<Account, Integer>{

	
	/*@Query ( "select s from Account s where "
			+ "(s.Account_name = :name )"
			+ "or ( s.IP_Geo_City = :city ) "
			+ "or ( s.IP_Geo_State = :state  )"
			+ "or ( s.IP_Geo_Country = :country )")
	Page<Account> x(@Param("name") String name, 
					@Param("city") String city,
					@Param("state") String state,
					@Param("country") String country,Pageable pageRequest);*/
	
	//Page<Account> findAll(Pageable pageRequest);
	Page<Account> findAllByNameContainingOrCityContainingOrStateContainingOrCountryContaining ( String name,String city, String state, String country,Pageable pageRequest);
}
