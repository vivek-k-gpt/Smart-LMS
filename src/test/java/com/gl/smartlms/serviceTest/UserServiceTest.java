package com.gl.smartlms.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.User;
import com.gl.smartlms.repository.UserRepository;
import com.gl.smartlms.service.UserServiceImpl;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;



@SpringBootTest(classes = { UserServiceTest.class })
class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BCryptPasswordEncoder beBCryptPasswordEncoder;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	@Order(1)
	void getAllTest() {

		List<User> users = new ArrayList<>();

		User user = getUser();
		users.add(user);

		when(userRepository.findAllByOrderByFirstNameAscMiddleNameAsc()).thenReturn(users);
		List<User> result = userServiceImpl.getAll();
		assertEquals(1, result.size());
	}

	@Test
	@Order(2)
	void getAllUserTest() {

		List<User> users = new ArrayList<>();

		User user = getUser();
		users.add(user);

		when(userRepository.findAll()).thenReturn(users);
		List<User> result = userServiceImpl.getAllUser();
		assertEquals(1, result.size());
	}

	@Test
	@Order(3)
	void getTotalCountTest() {
		List<User> users = new ArrayList<>();

		User user = getUser();
		users.add(user);
		users.add(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN));

		when(userRepository.count()).thenReturn((long) users.size());
		Long usersize = userServiceImpl.getTotalCount();
		assertEquals(users.size(), usersize);
	}


	@Test
	@Order(4)
	void getMemberTest() {

		List<User> users = new ArrayList<>();

		User user = getUser();
		users.add(user);

		User user2 = new User();
		user2.setId((long) 11);
		user2.setFirstName("vivek1");
		user2.setUsername("vivek12");
		user2.setContact("8527155866");
		user2.setDateOfBirth("08/08/1999");
		user2.setEmail("vivekgp8899@gmail.com");
		user2.setLastName("gupta");
		user2.setGender("male");
		user2.setRole("Admin");

		users.add(user2);

		when(userRepository.findById(10l)).thenReturn(findUser(10l, users));
		User us = userServiceImpl.getMember(10l).get();
		assertEquals("vivek1", us.getUsername());
	}

	@Test
	@Order(5)
	void saveTest() {
		User user = new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999",
				"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN);
		user.setId(1l);
		when(userRepository.save(user)).thenReturn(user);
		
		assertEquals(user, userServiceImpl.save(user));
	
	}
	
	
	
	@Test
	@Order(6)
	void deleteTest() {
		User user = new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999",
				"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN);
		user.setId(1l);
		userServiceImpl.deleteMember(1l);
		verify(userRepository,times(1)).deleteById(1l);
	}

	
	
	public Optional<User> findUser(Long id, List<User> users) {
		Optional<User> us = null;
		for (User user1 : users) {
			if (user1.getId() == id) {
				us = Optional.ofNullable(user1);
			}
		}
		return us;

	}

	private User getUser() {
		User user = new User();
		user.setId((long) 10);
		user.setFirstName("vivek");
		user.setUsername("vivek1");
		user.setContact("8527155866");
		user.setDateOfBirth("08/08/1999");
		user.setEmail("vivekgp8899@gmail.com");
		user.setLastName("gupta");
		user.setGender("male");
		user.setRole("Admin");
		return user;
	}

}
