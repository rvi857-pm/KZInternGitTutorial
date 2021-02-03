package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Account;

@Repository
public class RedisDao {
    
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String KEY = "ACCOUNT";
	
	public boolean saveAccount(Account account) {
		try {
			redisTemplate.opsForHash().put(KEY, account.getId(), account);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
}
