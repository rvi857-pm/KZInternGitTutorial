package com.example.demo.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import com.example.demo.model.Account;

public class CSVHelper {
  public static String TYPE = "application/vnd.ms-excel";
  static String[] HEADERs = {"id", "name", "ip_domain", "city", "state","country", "type","salesforce_id"};
  
  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
  }
  
  public static List<Account> csvToAccounts(InputStream is){
  
	  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<Account>accounts = new ArrayList<Account>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
		      
		      for(CSVRecord csvRecord : csvRecords) {
		    	  Account account = new Account(csvRecord.get("id"),csvRecord.get("name"), csvRecord.get("ip_domain"),csvRecord.get("city"), csvRecord.get("state"), csvRecord.get("country"), csvRecord.get("type"), csvRecord.get("salesforce_id"));
		    	  System.out.println(csvRecord.get("id"));
		    	  accounts.add(account);
		      }
         return accounts;
} catch(IOException e) {
	throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
}
  }
  
}
