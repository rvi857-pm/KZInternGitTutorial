package com.example.kz_assign.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.kz_assign.dao.account_repo;
import com.example.kz_assign.models.account;
import com.example.kz_assign.utilities.CSVHelper;

@Service
public class CSVService {
  @Autowired
  account_repo repository;

  public void save(MultipartFile file) throws Exception {
    try {
      List<account> accountslist = CSVHelper.csvToaccountslist(file.getInputStream());
      if(accountslist == null)
    	  throw new Exception("CSVHelper Error");
      else
    	  repository.saveAll(accountslist);
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }
}
