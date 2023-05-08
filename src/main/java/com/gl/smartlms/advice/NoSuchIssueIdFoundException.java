package com.gl.smartlms.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NoSuchIssueIdFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchIssueIdFoundException(String message) {
		super(message);
		
	}

	
}
