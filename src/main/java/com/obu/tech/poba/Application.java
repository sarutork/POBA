package com.obu.tech.poba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@ComponentScan("com.obu.tech")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
