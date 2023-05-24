package com.gl.smartlms.restControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.User;
import com.gl.smartlms.restController.UserRestController;
import com.gl.smartlms.service.UserService;
import com.gl.smartlms.service.UserServiceImpl;

@SpringBootTest(classes = { UserRestControllerTest.class })
public class UserRestControllerTest {

	@Mock
	UserService userService;

	@InjectMocks
	UserRestController userRestController;

	List<User> users;
	User user;

	@Test
	@Order(1)
	public void countAllUsersTest() {
		users = new ArrayList<>();
		users.add(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN));
		users.add(new User("admin1", "admin1", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN));

		when(userService.getTotalCount()).thenReturn((long) users.size());
		ResponseEntity<String> res = userRestController.countAllUsers();
		assertEquals(HttpStatus.OK, res.getStatusCode());

	}

//	
	@Test
	@Order(3)
	public void finduserByIdTest() {

		Optional<User> user = Optional.ofNullable(new User("admin", "admin", "male", "vivek", "kumar", "gupta",
				"08-08-1999", "vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN));
		user.get().setId(1l);
		when(userService.getMember(1l)).thenReturn(user);
		ResponseEntity<User> res = userRestController.findUserById(1l);
		assertEquals(HttpStatus.FOUND, res.getStatusCode());
		assertEquals(user.get().getId(), res.getBody().getId());
	}

	@Test
	@Order(4)
	public void registeruserTest() {
		user = new User("admin1", "admin1", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_FACULTY, Constants.ROLE_USER);

		when(userService.saveUser(user)).thenReturn(user);
		ResponseEntity<String> res = userRestController.registerUser(user);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}

	@Test
	@Order(5)
	public void registerLibrarianTest() {
		user = new User("admin1", "admin1", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_OTHER, Constants.ROLE_LIBRARIAN);

		when(userService.saveLibrarian(user)).thenReturn(user);
		ResponseEntity<String> res = userRestController.registerLibrarian(user);
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}

	@Test
	@Order(6)
	public void countAllFacultyMembersTest() {
		users = new ArrayList<>();
		users.add(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN));
		users.add(new User("admin1", "admin1", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_FACULTY, Constants.ROLE_USER));

		when(userService.getFacultyCount()).thenReturn(1l);
		ResponseEntity<String> res = userRestController.countAllFacultyMembers();
		assertEquals(HttpStatus.OK, res.getStatusCode());

	}

	@Test
	@Order(7)
	public void countAllStudentMembersTest() {

		users = new ArrayList<>();
		users.add(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_STUDENT, Constants.ROLE_USER));
		users.add(new User("admin1", "admin1", "male", "vivek", "kumar", "gupta", "08-08-1999", "vivekgp8899@gmail.com",
				"8527648963", Constants.MEMBER_FACULTY, Constants.ROLE_USER));

		when(userService.getFacultyCount()).thenReturn(1l);
		ResponseEntity<String> res = userRestController.countAllStudentMembers();
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	
	
	@Test
	@Order(8)
	public void  showAllStudentsTest() {
		users = new ArrayList<>();
		users.add(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999",
					"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_STUDENT, Constants.ROLE_USER));
		users.add(new User("admin1", "admin1", "male", "vivek", "kumar", "gupta", "08-08-1999",
				"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_STUDENT, Constants.ROLE_USER));
	
	when(userService.getAllStudent()).thenReturn(users);
	ResponseEntity<List<User>> res = userRestController.showAllStudents();
	assertEquals(HttpStatus.FOUND, res.getStatusCode());
	assertEquals(2, res.getBody().size());
	}
	
	
	
	@Test
	@Order(9)
	public void  showAllFacultiesTest() {
		users = new ArrayList<>();
		users.add(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999",
					"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_FACULTY, Constants.ROLE_USER));
		users.add(new User("admin1", "admin1", "male", "vivek", "kumar", "gupta", "08-08-1999",
				"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_FACULTY, Constants.ROLE_USER));
	
	when(userService.getAllFaculty()).thenReturn(users);
	ResponseEntity<List<User>> res = userRestController.showAllFaculties();
	assertEquals(HttpStatus.FOUND, res.getStatusCode());
	assertEquals(2, res.getBody().size());
	}
	
	
	
	
	
	
	
}
