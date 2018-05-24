package com.seebattleserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static Class applicationClass = Application.class;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
