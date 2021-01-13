package com.example.pagination;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository  extends CrudRepository<Account, Integer> {
	@Query(value="select * from account where "
			+ "account_name like :#{#account.account_name} and "
			+ "ip_domain like :#{#account.ip_domain} and "
			+ "ip_geo_city like :#{#account.ip_geo_city} and "
			+ "ip_geo_state like :#{#account.ip_geo_state} and "
			+ "ip_geo_country like :#{#account.ip_geo_country} and "
			+ "type like :#{#account.type} and "
			+ "sfdc_account_id like :#{#account.sfdc_account_id} limit :#{#offset}, :#{#limit}", nativeQuery=true)
	public List<Account> findBysearch(@Param("account") Account account, @Param("offset") int offset, @Param("limit") int limit);
	
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