package com.example.kz_assign.dao;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class Redis_repo {
	private RedisTemplate<String, Map<String,Object>> redisTemplate;
    @SuppressWarnings("rawtypes")
	private HashOperations hashOperations; 
    
//    private ListOperations listOperations;
    
    public Redis_repo(RedisTemplate<String, Map<String,Object>> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = this.redisTemplate.opsForHash();
    }
    
    @SuppressWarnings("unchecked")
	public void save(Map<String,Object> datamapsample) {
//    	if(hashOperations.size("accountsdata") >= 700) {
//    		//listOperations.rightPop("accountsdata");
//    		return;
//    	}
    	hashOperations.put("accountdata",datamapsample.get("id"),datamapsample);
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,Map<String,Object>> findAll() {
        return hashOperations.entries("accountdata");
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,Object> findById(String id) {
        return (Map<String,Object>)hashOperations.get("accountdata",id);
    }
    
    
    public void update(Map<String,Object> datamapsample) {
      save(datamapsample);
    }
    
    @SuppressWarnings("unchecked")
	public void delete(String id) {
       hashOperations.delete("accountdata",id);
    }
}
