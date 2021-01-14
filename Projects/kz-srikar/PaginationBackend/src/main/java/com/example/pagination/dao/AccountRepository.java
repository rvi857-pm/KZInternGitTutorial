package com.example.pagination.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.pagination.model.Account;

import java.util.List;

public interface AccountRepository  extends JpaRepository<Account, Integer> {
	@Query(value="select * from account where "
			+ "account_name like :#{#name} or "
			+ "ip_domain like :#{#name} or "
			+ "ip_geo_city like :#{#name} or "
			+ "ip_geo_state like :#{#name} or "
			+ "ip_geo_country like :#{#name} or "
			+ "type like :#{#name} or "
			+ "sfdc_account_id like :#{#name} limit :#{#offset}, :#{#limit}", nativeQuery=true)
	public List<Account> findBySingleSearch(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);
}