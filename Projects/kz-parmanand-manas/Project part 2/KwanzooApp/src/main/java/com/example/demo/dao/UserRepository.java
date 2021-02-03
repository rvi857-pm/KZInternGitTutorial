package com.example.demo.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.model.Account;

//JPA repository helpful in PaginationansSorting and retrieving data from database 
@Repository
public interface UserRepository extends JpaRepository<Account, String> {
//Universal search without pagination
	
	public void save(MultipartFile file);
	
	
	@Query("select u from Account u where u.name LIKE %?1%"
			   + "AND u.ip_domain LIKE %?2%"
			   + "AND u.salesforce_id LIKE %?3%"
			   + "AND u.city LIKE %?4%"
			   + "AND u.type LIKE %?5%"
			   + "AND u.country LIKE %?6%"
			   + "AND u.state LIKE %?7%"
			   + "AND ( u.name LIKE %?8%"
			   + "OR u.ip_domain LIKE %?8%"
			   + "OR u.salesforce_id LIKE %?8%"
			   + "OR u.city LIKE %?8%"
			   + "OR u.type LIKE %?8%"
			   + "OR u.country LIKE %?8%"
			   + "OR u.state LIKE %?8% )"
				)
	public List<Account> findByU_Search(String name, String ip_domain , String sfdc_id,  String city, String type, String country, String state, String keyword);
	
//Universal search with pagination
	
	@Query("select u from Account u where u.name LIKE %?1%"
			   + "AND u.ip_domain LIKE %?2%"
			   + "AND u.salesforce_id LIKE %?3%"
			   + "AND u.city LIKE %?4%"
			   + "AND u.type LIKE %?5%"
			   + "AND u.country LIKE %?6%"
			   + "AND u.state LIKE %?7%"
			   + "AND ( u.name LIKE %?8%"
			   + "OR u.ip_domain LIKE %?8%"
			   + "OR u.salesforce_id LIKE %?8%"
			   + "OR u.city LIKE %?8%"
			   + "OR u.type LIKE %?8%"
			   + "OR u.country LIKE %?8%"
			   + "OR u.state LIKE %?8% )"
				)
	public List<Account> findByU_SearchP(String name, String ip_domain , String sfdc_id,  String city, String type, String country, String state, String keyword, Pageable paging);

}
