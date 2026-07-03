package com.example.bookmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookmanagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookmanagementApplication.class, args);
	}
}