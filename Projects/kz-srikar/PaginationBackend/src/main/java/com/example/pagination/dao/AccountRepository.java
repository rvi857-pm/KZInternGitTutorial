package com.example.pagination.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pagination.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}