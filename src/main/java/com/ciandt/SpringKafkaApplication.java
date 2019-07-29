package com.ciandt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.JsonMappingException;

@SpringBootApplication
public class SpringKafkaApplication {
	public static void main(String[] args) throws JsonMappingException {	
		SpringApplication.run(SpringKafkaApplication.class, args);
	}
}