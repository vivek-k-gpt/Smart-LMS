package com.gl.smartlms.exception;



import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gl.smartlms.model.ApiError;

import com.gl.smartlms.customexception.*;





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

//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(NoSuchIssueIdFoundException.class)
//	public Map<String, String> handleNosuchIssueIdFoundException(NoSuchIssueIdFoundException error){
//		
//		Map<String, String> errorMap = new HashMap<String,String>();
//		errorMap.put("message", error.getMessage());
//		return errorMap;
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@ExceptionHandler(NoSuchIssueIdFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(NoSuchIssueIdFoundException ex){
		String message = ex.getMessage();
		List<String> details = new ArrayList<>();
		details.add("Issue id Not Found");
		ApiError errors = new ApiError(message,details,HttpStatus.BAD_REQUEST,LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	
	
	
	
	
	
	




}
