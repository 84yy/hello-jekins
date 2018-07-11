package com.hzm.hellojekins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloJekinsApplication {

	@GetMapping("/")
	public String hello(){
		return "Hello Jekins";
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloJekinsApplication.class, args);
	}
}
