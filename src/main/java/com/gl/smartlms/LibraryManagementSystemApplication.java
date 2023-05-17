package com.gl.smartlms;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/lms");

		SpringApplication.run(LibraryManagementSystemApplication.class, args);
	}

}
