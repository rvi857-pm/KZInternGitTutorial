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
    
    public Redis_repo(RedisTemplate<String, Map<String,Object>> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = this.redisTemplate.opsForHash();
    }
    
    @SuppressWarnings("unchecked")
	public void save(Map<String,Object> datamapsample, String domainname) {
    	hashOperations.put(domainname,datamapsample.get("id"),datamapsample);
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,Map<String,Object>> findAll(String domainname) {
        return hashOperations.entries(domainname);
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,Object> findById(String id, String domainname) {
        return (Map<String,Object>)hashOperations.get(domainname,id);
    }
    
    
    public void update(Map<String,Object> datamapsample) {
      save(datamapsample,"accountdata");
    }
    
    @SuppressWarnings("unchecked")
	public void delete(String id) {
       hashOperations.delete("accountdata",id);
    }
}
