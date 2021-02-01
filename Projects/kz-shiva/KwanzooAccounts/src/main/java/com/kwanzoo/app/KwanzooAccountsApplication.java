package com.kwanzoo.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableCaching
public class KwanzooAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KwanzooAccountsApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/accounts").allowedOrigins("http://localhost:3000");
				registry.addMapping("/upload-accounts").allowedOrigins("http://localhost:3000");
			}
		};
	}

}
