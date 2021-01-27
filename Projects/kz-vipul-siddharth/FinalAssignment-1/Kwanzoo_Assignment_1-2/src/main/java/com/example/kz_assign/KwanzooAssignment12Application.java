package com.example.kz_assign;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class KwanzooAssignment12Application {

	@Bean 
    public static LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lcf = new LettuceConnectionFactory();		
        lcf.afterPropertiesSet();
        return lcf;
    }
	
	@Bean
	public static RedisTemplate<String, Map<String,Object>> redisTemplate() {
	    RedisTemplate<String,  Map<String,Object>> redisTemplate = new RedisTemplate<String , Map<String,Object>>();
	    redisTemplate.setConnectionFactory(redisConnectionFactory());
	    redisTemplate.afterPropertiesSet();
	    return redisTemplate;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(KwanzooAssignment12Application.class, args);
	}

}
