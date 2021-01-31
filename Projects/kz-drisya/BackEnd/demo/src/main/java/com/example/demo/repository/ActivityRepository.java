package com.example.demo.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Activity;


public interface ActivityRepository extends JpaRepository <Activity, String>{

	Page<Activity> findAllByBuyerId(String buyer_id, Pageable paging);
}
