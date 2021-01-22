package com.kwanzoo.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kwanzoo.project.model.Buyer;


@Repository
public interface BuyerDao extends JpaRepository<Buyer, String> {
	
}
