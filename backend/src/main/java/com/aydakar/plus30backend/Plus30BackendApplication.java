package com.aydakar.plus30backend;

import com.aydakar.plus30backend.util.LCUConnector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableScheduling
public class Plus30BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Plus30BackendApplication.class, args);
	}

	@Override
	public void run(String... arg0)throws IOException{

	}
}


