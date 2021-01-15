package com.example.kz_assign.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.kz_assign.models.account;



public interface account_repo extends JpaRepository<account,Integer> {
	@Query(nativeQuery=true,value="Select * from acc where city regexp ?1")
	List<account> findByIPGeoCity(String IPGeoCity);
	@Query(nativeQuery=true,value="Select * from acc where state regexp ?1")
	List<account> findByIPGeoState(String IPGeoState);
	@Query(nativeQuery=true,value="Select * from acc where country regexp ?1")
	List<account> findByIPGeoCountry(String IPGeoCountry);
	//List<account> findByaccountName(String accountname);
	
	@Query(nativeQuery=true,value="Select * from acc where name regexp ?1")
	List<account> findByaccountName(String accountname);
}