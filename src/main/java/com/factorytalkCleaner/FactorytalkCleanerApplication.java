package com.factorytalkCleaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FactorytalkCleanerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactorytalkCleanerApplication.class, args);
	}

}
