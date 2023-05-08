package com.gl.smartlms.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

	
	String message;
	List<String> details;
	HttpStatus status;
	LocalDateTime timestamp;
}
