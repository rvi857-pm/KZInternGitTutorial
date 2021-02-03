package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, String> {

	@Query("Select b from Activity b")
	public List<Activity>findAll_P(Pageable paging);
	
	
	@Query("Select b from Activity b")
	public List<Activity>findAll_WP();
	
	@Query("Select b from Activity b where b.buyer_id = ?1")
	public List<Activity>findById_P(String buyer_id, Pageable paging);

	@Query("Select b from Activity b where b.buyer_id = ?1")
	public List<Activity>findById_WP(String buyer_id);
}
