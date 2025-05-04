package com.kiago.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*KIAGO API*/
@SpringBootApplication
public class KiagoApiApplication {

	private String API= "Kiago Api";
	public static void main(String[] args) {
		SpringApplication.run(KiagoApiApplication.class, args);
	}

}
