package com.aydakar.plus30backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableScheduling
public class Plus30BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Plus30BackendApplication.class, args);
	}

	@Override
	public void run(String... arg0){
		System.out.println("Server is running on default port!");
	}
}


