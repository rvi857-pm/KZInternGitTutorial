package com.example.pagination.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pagination.model.Account;
import com.example.pagination.model.Buyer;

public interface BuyerRepository extends JpaRepository<Buyer, Integer> {

	public List<Buyer> findByAccount(Account account);
}