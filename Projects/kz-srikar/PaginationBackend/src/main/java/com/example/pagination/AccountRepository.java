package com.example.pagination;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository  extends CrudRepository<Account, Integer> {
	
	@Query(value="select * from account limit ?1, ?2", nativeQuery=true)
	public List<Account> findPage(int offset, int limit);
	
	@Query(value="select * from account where account_name1 = ?1 limit ?2, ?3", nativeQuery=true)
	public List<Account> findPageAndAccountId(String account_name, int offset, int limit);
	
	@Query(value="select * from account where "
			+ "account_name1 like :#{#account.account_name1} and "
			+ "ip_domain like :#{#account.ip_domain} and "
			+ "ip_geo_city like :#{#account.ip_geo_city} and "
			+ "ip_geo_state like :#{#account.ip_geo_state} and "
			+ "ip_geo_country like :#{#account.ip_geo_country} and "
			+ "type like :#{#account.type} and "
			+ "sfdc_account_id like :#{#account.sfdc_account_id} and "
			+ "account_name2 like :#{#account.account_name2} and "
			+ "account_id like :#{#account.account_id} limit :#{#offset}, :#{#limit}", nativeQuery=true)
	public List<Account> findBysearch(@Param("account") Account account, @Param("offset") int offset, @Param("limit") int limit);
}