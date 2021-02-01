package com.kwanzoo.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kwanzoo.app.model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Integer> {

}