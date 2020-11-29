package com.navyug.atm.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.navyug.atm.api.controller.ATMController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.navyug.atm.api"}, basePackageClasses = {ATMController.class})
@EnableJpaRepositories(basePackages = "com.navyug.atm.api", considerNestedRepositories = true)
public class AtmApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmApiApplication.class, args);
	}

}
