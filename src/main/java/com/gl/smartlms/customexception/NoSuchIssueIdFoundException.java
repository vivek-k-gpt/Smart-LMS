package com.gl.smartlms.customexception;

public class NoSuchIssueIdFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchIssueIdFoundException(String message) {
		super(message);
		
	}

	public NoSuchIssueIdFoundException() {
	
	}

	
}
