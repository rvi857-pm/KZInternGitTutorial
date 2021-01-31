package com.example.pagination.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pagination.model.Activity;
import com.example.pagination.model.Buyer;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {

	public List<Activity> findByBuyer(Buyer buyer);
}