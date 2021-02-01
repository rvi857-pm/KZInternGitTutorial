package com.kwanzoo.app.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kwanzoo.app.model.Account;
import com.kwanzoo.app.repo.AccountRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class StorageService {

	@Autowired
	AccountRepository accountRepository;

	public Object store(MultipartFile file) {

		if (file.isEmpty()) {
			return "Empty File";
		} else {
			try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

				CsvToBean<Account> csvToBean = new CsvToBeanBuilder<Account>(reader).withType(Account.class)
						.withIgnoreLeadingWhiteSpace(true).build();

				List<Account> accounts = csvToBean.parse();

				for (int i = 0; i < accounts.size(); i++) {
					Account account = accounts.get(i);
					UUID uuid = UUID.randomUUID();
					account.setId(uuid.toString());
					try {
						accountRepository.save(account);
					} catch (IllegalArgumentException  ex) {
						return ex.toString();
					}
				}
				return "Successfully added accounts to database";

			} catch (Exception ex) {
				return ex.toString();
			}
		}
	}
}
