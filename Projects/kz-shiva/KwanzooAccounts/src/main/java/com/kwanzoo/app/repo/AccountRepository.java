package com.kwanzoo.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kwanzoo.app.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}