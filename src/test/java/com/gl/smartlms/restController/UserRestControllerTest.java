package com.gl.smartlms.restController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gl.smartlms.model.User;
import com.gl.smartlms.service.UserService;

@SpringBootTest
public class UserRestControllerTest {
	
	
	
	@Autowired
	UserRestController ucon;
	
	@MockBean
	UserService uservice;
	
	
//	
	@Test
	public void checkFindByUserId() {
		Optional<User> user = Optional.ofNullable(buildUser());
		when((uservice.getMember(user.get().getId()))).thenReturn(user);
		ResponseEntity response = ucon.findUserById(user.get().getId());
		System.out.println(response.getStatusCode());
		assertEquals(response.getStatusCode(), HttpStatus.FOUND);
		User userr = (User) response.getBody();
		System.out.println(userr.getId());
		assertEquals(userr.getId(), user.get().getId());
	}

	
	
	
	public User buildUser() {
		User user = new User();
		user.setUsername("vivek");
		user.setContact("8527155866");
		user.setDateOfBirth("22-03-1999");
		user.setEmail("vivekgp8899@gmail.com");
		user.setFirstName("vivek");
		user.setMiddleName("kumar");
		user.setLastName("gupta");
		user.setPassword("1234");
		user.setGender("male");
		user.setId((long) 10);
		return user;
		}
}
