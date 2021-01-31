package com.example.kz_assign.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.example.kz_assign.models.account;


public class CSVHelper {
  public static String TYPE = "text/csv";
  static String[] HEADERs = { "Account Name", "IP Geo City", "IP Geo State", "IP Geo Country", "IP Domain", "Type", "SFDC Account Id" };

  public static boolean hasCSVFormat(MultipartFile file) {
    if (file.getContentType().equals("application/vnd.ms-excel")
    		|| file.getContentType().equals("text/csv")
    		|| file.getContentType().equals("application/vnd.openxmlfornats-officedocument.spreadsheetml.sheet")) {
      return true;
    }

    return false;
  }

  public static List<account> csvToaccountslist(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<account> accountsList = new ArrayList<>();

      List<String> csvheaders =  csvParser.getHeaderNames();
      for(String header:HEADERs) {
    	  if(!(csvheaders.contains(header))) {
    		  return null;
    	  }
      }
      
      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
    	  account acc = new account(
              csvRecord.get("Account Name"),
              csvRecord.get("IP Geo City"),
              csvRecord.get("Ip Geo State"),
              csvRecord.get("IP Geo Country"),
              csvRecord.get("Type"),
              csvRecord.get("Ip Domain"),
              csvRecord.get("SFDC Account Id")
            );

    	  accountsList.add(acc);
      }

      return accountsList;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }
}

