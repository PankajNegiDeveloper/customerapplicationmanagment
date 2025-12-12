package com.rest.cam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CustomerApplicationManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplicationManagmentApplication.class, args);
		
		//System.out.println(new BCryptPasswordEncoder().encode("admin123"));
	}

}
