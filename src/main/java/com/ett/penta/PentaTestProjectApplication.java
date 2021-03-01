package com.ett.penta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class PentaTestProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PentaTestProjectApplication.class, args);
	}

}
