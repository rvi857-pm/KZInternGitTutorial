
              package com.example.demo;
              import org.springframework.boot.SpringApplication;
              import org.springframework.boot.autoconfigure.SpringBootApplication;
              import org.springframework.web.bind.annotation.GetMapping;
              import org.springframework.web.bind.annotation.RequestParam;
              import org.springframework.web.bind.annotation.RestController;
              
              import org.springframework.boot.web.servlet.FilterRegistrationBean;
              import org.springframework.context.annotation.Bean;
              import org.springframework.core.Ordered;
              import org.springframework.web.cors.CorsConfiguration;
              import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
              import org.springframework.web.filter.CorsFilter;
              import java.util.Collections;
              
              @SpringBootApplication
              @RestController
              public class DemoApplication {
                
                  
                  public static void main(String[] args) {
                  SpringApplication.run(DemoApplication.class, args);
                  }
                  
                  @GetMapping("/hello")
                  public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
                  return String.format("Hello !");
                  }
                  
                  @Bean
                  public FilterRegistrationBean simpleCorsFilter() {  
                      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
                      CorsConfiguration config = new CorsConfiguration();  
                      config.setAllowCredentials(true); 
                      // *** URL below needs to match the Vue client URL and port ***
                      config.setAllowedOrigins(Collections.singletonList("http://localhost:8081")); 
                      config.setAllowedMethods(Collections.singletonList("*"));  
                      config.setAllowedHeaders(Collections.singletonList("*"));  
                      source.registerCorsConfiguration("/**", config);  
                      FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
                      bean.setOrder(Ordered.HIGHEST_PRECEDENCE);  
                      return bean;  
                  } 
                
              }
            