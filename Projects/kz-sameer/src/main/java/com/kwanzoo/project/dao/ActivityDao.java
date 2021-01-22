package com.kwanzoo.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kwanzoo.project.model.Activity;


@Repository
public interface ActivityDao extends JpaRepository<Activity, Integer> {
	
}
