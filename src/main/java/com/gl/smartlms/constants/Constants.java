package com.gl.smartlms.constants;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


//==============================================================
			// = All Constants
//==============================================================
public class Constants {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_LIBRARIAN = "ROLE_LIBRARIAN";
	public static final String ROLE_USER = "ROLE_USER";

	public static final String MEMBER_FACULTY = "Faculty";
	public static final String MEMBER_STUDENT = "Student";
	public static final String MEMBER_OTHER = "Other";


	public static final Integer BOOK_STATUS_AVAILABLE = 1;
	public static final Integer BOOK_STATUS_ISSUED = 2;

	public static final Integer BOOK_NOT_RETURNED = 0;
	public static final Integer BOOK_RETURNED = 1;

	

	public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpstatus) {

		return new ResponseEntity<String>("{\"message\" :\"" + responseMessage + "\"}", httpstatus);
	}

}
