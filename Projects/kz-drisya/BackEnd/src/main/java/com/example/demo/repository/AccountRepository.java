package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	
	@Query ( "select s from Account s where "
			+ "(s.Account_name = :name or :name is null)"
			+ "and ( s.IP_Geo_City = :city or :city is null) "
			+ "and ( s.IP_Geo_State = :state or :state is null)"
			+ "and ( s.IP_Geo_Country = :country or :country is null )")
	Page<Account> x(@Param("name") String name, 
					@Param("city") String city,
					@Param("state") String state,
					@Param("country") String country,Pageable pageRequest);
}
