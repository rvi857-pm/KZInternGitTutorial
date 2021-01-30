package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.AccountScore;


public interface AccountScoreRepository  extends JpaRepository<AccountScore, String>{

	@Query ( " SELECT score FROM AccountScore as a WHERE a.accountId = :Id")
	Integer findScore( @Param("Id") String id);
}
