package com.practice.user_session_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class UserSessionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSessionServiceApplication.class, args);
	}

}
