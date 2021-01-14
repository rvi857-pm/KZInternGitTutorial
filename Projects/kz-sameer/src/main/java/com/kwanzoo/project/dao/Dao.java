package com.kwanzoo.project.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kwanzoo.project.model.Account;

@Repository
public interface Dao extends JpaRepository<Account, Integer> {
	
}
