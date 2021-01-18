package com.example.demo.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.User;

//JPA repository helpful in PaginationansSorting and retrieving data from database 
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
   
	
	
//Universal search without pagination
	
	@Query("select u from User u where u.Account_Name LIKE %?1%"
			   + "AND u.Ip_Domain LIKE %?2%"
			   + "AND u.SFDC_Account_Id LIKE %?3%"
			   + "AND u.Ip_Geo_City LIKE %?4%"
			   + "AND u.Types LIKE %?5%"
			   + "AND u.Ip_Geo_Country LIKE %?6%"
			   + "AND u.Ip_Geo_State LIKE %?7%"
			   + "AND ( u.Account_Name LIKE %?8%"
			   + "OR u.Ip_Domain LIKE %?8%"
			   + "OR u.SFDC_Account_Id LIKE %?8%"
			   + "OR u.Ip_Geo_City LIKE %?8%"
			   + "OR u.Types LIKE %?8%"
			   + "OR u.Ip_Geo_Country LIKE %?8%"
			   + "OR u.Ip_Geo_State LIKE %?8% )"
				)
	public List<User> findByU_Search(String name, String ip_domain , String sfdc_id,  String city, String type, String country, String state, String keyword);
	
//Universal search with pagination
	
	@Query("select u from User u where u.Account_Name LIKE %?1%"
			   + "AND u.Ip_Domain LIKE %?2%"
			   + "AND u.SFDC_Account_Id LIKE %?3%"
			   + "AND u.Ip_Geo_City LIKE %?4%"
			   + "AND u.Types LIKE %?5%"
			   + "AND u.Ip_Geo_Country LIKE %?6%"
			   + "AND u.Ip_Geo_State LIKE %?7%"
			   + "AND ( u.Account_Name LIKE %?8%"
			   + "OR u.Ip_Domain LIKE %?8%"
			   + "OR u.SFDC_Account_Id LIKE %?8%"
			   + "OR u.Ip_Geo_City LIKE %?8%"
			   + "OR u.Types LIKE %?8%"
			   + "OR u.Ip_Geo_Country LIKE %?8%"
			   + "OR u.Ip_Geo_State LIKE %?8% )"
				)
	public List<User> findByU_SearchP(String name, String ip_domain , String sfdc_id,  String city, String type, String country, String state, String keyword, Pageable paging);
	
	
}
