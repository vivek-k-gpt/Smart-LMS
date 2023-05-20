package com.gl.smartlms.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

//====================================================================================
// = Class to Help In Displaying Formatted Validation Failed Messages 
// ====================================================================================
@Data
@AllArgsConstructor
public class ApiError {

	String message;
	List<String> details;
	HttpStatus status;
	LocalDateTime timestamp;
}
