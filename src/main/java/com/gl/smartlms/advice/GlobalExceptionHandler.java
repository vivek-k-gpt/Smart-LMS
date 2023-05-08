package com.gl.smartlms.advice;



import java.time.LocalDateTime;


import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import com.gl.smartlms.model.ApiError;





//==============================================================
			// = Exception
//==============================================================
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException error) {
		
		Map<String, String> errorMap = new HashMap<String, String>();
		
		error.getBindingResult().getFieldErrors().forEach(err -> {
			errorMap.put(err.getField(), err.getDefaultMessage());
		});
		
		return errorMap;
	}

	
	
	
	
	
	

	
//	@ExceptionHandler(NoSuchIssueIdFoundException.class)
//	public ResponseEntity<String> handleNosuchIssueIdFoundException(NoSuchIssueIdFoundException error){
//		
//		return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.getMessage());
//	}
	
	
	
	
	
	
	@ExceptionHandler(NoContentFoundException.class)
	public ResponseEntity<Object> handleNoContent(NoContentFoundException ex){
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Result Is Empty");
		ApiError errors = new ApiError(message,details,HttpStatus.NOT_FOUND,LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}
	
	
	
	

	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex){
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("User Is not Available");
		ApiError errors = new ApiError(message,details,HttpStatus.NOT_FOUND,LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}
	
	
	
	
	
	@ExceptionHandler(NoSuchIssueIdFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(NoSuchIssueIdFoundException ex){
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Issue id Not Found");
		ApiError errors = new ApiError(message,details,HttpStatus.NOT_FOUND,LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}
	
	
	
	@ExceptionHandler(RegistrationFailedException.class)
	public ResponseEntity<Object> handleRegistrationFailedException(RegistrationFailedException ex){
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Try Again Later");
		ApiError errors = new ApiError(message,details,HttpStatus.NOT_ACCEPTABLE,LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errors);
	}
	
	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<Object> handleAuthenticationFailedException(AuthenticationFailedException ex){
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Please Use Valid Credentials");
		ApiError errors = new ApiError(message,details,HttpStatus.NOT_FOUND,LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
	}
	
	
	
	
	


	
	
	
	
	
	
	




}
