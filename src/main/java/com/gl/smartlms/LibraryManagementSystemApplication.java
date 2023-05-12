package com.gl.smartlms;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;



@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Library APIs",version= "1.0",description = "Library Management System"))
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
	    System.setProperty("server.servlet.context-path", "/lms");

		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

}
