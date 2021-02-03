package com.example.demo.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Buyer;


@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Integer>{

	@Query("Select b from Buyer b")
	public List<Buyer>findAll_WP();
	
	
	@Query("Select b from Buyer b")
	public List<Buyer>findAll_P(Pageable paging);
	
	
	@Query("Select b from Buyer b where b.account_id = ?1")
	public List<Buyer>findByAccountId_WP(String id);

	@Query("Select b from Buyer b where b.account_id = ?1")
	public List<Buyer>findByAccountId_P(String id, Pageable paging);
	
}
