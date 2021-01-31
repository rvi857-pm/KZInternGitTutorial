package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Account;
import com.example.demo.model.AccountCSV;
import com.example.demo.repository.AccountRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class FileService {

	
	@Autowired 
	AccountRepository repo;
	
	private Account setAll(String id, AccountCSV accountcsv) {
		
		Account account = new Account();
		account.setId(id);
		account.setCity(accountcsv.getCity());
		account.setName(accountcsv.getName());
		account.setCountry(accountcsv.getCountry());
		account.setIpDomain(accountcsv.getDomain());
		account.setState(accountcsv.getState());
		account.setType(accountcsv.getType());
		account.setSalesforceId(accountcsv.getSalesforce());
		return account;
	}
	
	private void uploadDB( List<AccountCSV> accounts) {
		
		for ( int i = 0; i < accounts.size(); i++) {
		
			UUID uuid=UUID.randomUUID();
			Account account = setAll(uuid.toString(), accounts.get(i));
			repo.save(account);
		}
		
	}
	public String uploadFile( MultipartFile file) {
		
		if( file.isEmpty())
			return " Please select the file";
		
		try( Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
			
			CsvToBean<AccountCSV> csvToBean = new CsvToBeanBuilder(reader)
					.withType(AccountCSV.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build();
			
			
			List<AccountCSV> accounts = csvToBean.parse();
			
			uploadDB(accounts);
			return "uploaded successfully";
		}catch( Exception ex) {
			System.out.println(ex);
			return " server-error";
		}
	}
}
