package com.gl.smartlms.restController;

import java.util.List;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.smartlms.advice.AuthenticationFailedException;
import com.gl.smartlms.advice.RegistrationFailedException;
import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.User;
import com.gl.smartlms.service.UserService;

@RestController
@RequestMapping("/api-user")
public class UserRestController {

	@Autowired
	private UserService userService;
	ObjectMapper Obj = new ObjectMapper();

	// ==============================================================
	// User Login API	(User+Admin)
	// ==============================================================

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getUserValidate(@RequestParam String username, @RequestParam String password) {

		Optional<User> user = Optional.ofNullable(userService.getUserValidate(username, password));

		if (user.isEmpty()) {
			throw new AuthenticationFailedException("Authentication Failed ...... Invalid Credentials");
		}

		if (user.get().getActive() == 1) {
			return new ResponseEntity<String>("User Logged in Succesfully", HttpStatus.OK);

		} else {

			return new ResponseEntity<String>("User Not Active.......Contact Administration", HttpStatus.OK);
		}

	}

	// ==============================================================
	// User Register API		(User)
	// ==============================================================

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
		user.setActive(0);		user.setRole("User");
		Optional<User> user1 = Optional.ofNullable(userService.registerUser(user));

		if (user1.isEmpty()) {
			throw new RegistrationFailedException("Registration Failed");
		}

		return new ResponseEntity<String>("User Registered Sucessfully", HttpStatus.CREATED);

	}

	
	

	// ==============================================================
	// Add Librarian API API				(Admin)
	// ==============================================================
	@RequestMapping(value = "/admin/register", method = RequestMethod.POST)
	public ResponseEntity<String> saveMember(@Valid @RequestBody User user) {
			user.setActive(1); user.setRole(Constants.ROLE_LIBRARIAN);
		Optional<User> user1 = Optional.ofNullable(userService.registerUser(user));
		if(user1.isEmpty()) {
			throw new RegistrationFailedException("Registration Failed ......");
			}
		return new ResponseEntity<String>("User Registered Successfully", HttpStatus.CREATED);
	}
	
	
	

	// ============================================================== ============================================================== ==============================================================//

	
	
	
	
	
	
	
	
	
	
	
	// ==============================================================
	// User Count API		(Admin)
	// ==============================================================

	@GetMapping("/count")
	public ResponseEntity<String> countAllUsers() {
		Long userCount = userService.getTotalCount();
		if (userCount != 0) {
			return new ResponseEntity<String>(userCount.toString(), HttpStatus.OK);
		} else {
			return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
		}
		
	}

	// ==============================================================
	// Faculty Member Count API   (Admin)
	// ==============================================================

	@GetMapping("/count/faculty")
	public ResponseEntity<String> countAllFacultyMembers() {
		Long facultyCount = userService.getFacultyCount();
		if (facultyCount != 0) {
			return new ResponseEntity<String>(facultyCount.toString(), HttpStatus.OK);
		}
		return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// Student Member Count API		(Admin)
	// ==============================================================

	@GetMapping("/count/student")
	public ResponseEntity<String> countAllStudentMembers() {
		Long studentCount = userService.getStudentsCount();
		if (studentCount != 0) {
			return new ResponseEntity<String>(studentCount.toString(), HttpStatus.OK);
		}
		return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
	}


	// ==============================================================
	// List Users Api(Sorted)			(Admin)
	// ==============================================================
	@GetMapping("/user")
	public ResponseEntity<List<User>> showAllUsers() {
		List<User> list = userService.getAll();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}
	
	
	// ==============================================================
	// List Student Member Api (Admin)
	// ==============================================================
	@GetMapping("/student")
	public ResponseEntity<List<User>> showAllStudents() {
		List<User> list = userService.getAllStudent();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}

	
	
	// ==============================================================
	// List Faculty Member Api		(Admin)
	// ==============================================================
	@GetMapping("/faculty")
	public ResponseEntity<List<User>> showAllFaculties() {
		List<User> list = userService.getAllFaculty();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}

	// ==============================================================
	// List Active Member Api		(Admin)
	// ==============================================================
	@GetMapping("/active")
	public ResponseEntity<List<User>> showAllActive() {
		List<User> list = userService.getAllActive();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}


	// ==============================================================
	// List Active Member Api		(Admin)
	// ==============================================================
	@GetMapping("/inactive")
	public ResponseEntity<List<User>> showAllInActive() {
		List<User> list = userService.getAllInActive();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}


	// ==============================================================
	// Blocking Member Api			(Admin)
	// ==============================================================
	@PutMapping("/block/{id}")
	public ResponseEntity<String> blockUser(@PathVariable Long id) {
		User user = userService.getMember(id).get();
		if (user.getActive() == 1) {
			user.setActive(0);
			userService.save(user);
			return new ResponseEntity<String>("User Blocked Successfully", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("User is already Blocked", HttpStatus.OK);
		
	}

	// ==============================================================
	// UnBlocking Member Api		(Admin)
	// ==============================================================

	@PutMapping("/unblock/{id}")
	public ResponseEntity<String> unlockUser(@PathVariable Long id) {
		User user = userService.getMemberById(id);
		if (user.getActive() == 0) {
			user.setActive(1);
			userService.save(user);
			return new ResponseEntity<String>("User UnBlocked", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("User is Not Blocked", HttpStatus.ACCEPTED);
	}

	// ==============================================================
	// Find Member API(change)		(Admin)
	// ==============================================================

	@GetMapping(value = "/find/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Long id) {
		User user = userService.getMember(id).get();
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}
			


	// ==============================================================
	// Update Member API		(Admin)
	// ==============================================================

	@PutMapping("/update")
	public ResponseEntity<String> updateMember(@Valid @RequestBody User member) {
		Optional<User> member1 = userService.getMember(member.getId());
		userService.save(member);
		return new ResponseEntity<String>("Member Updated With Name " + member1.get().getFirstName(),
				HttpStatus.ACCEPTED);
	}

	
	// ==============================================================
	// Delete Member API		(Admin)
	// ==============================================================
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeUser(@PathVariable Long id) {
		User member = userService.getMember(id).get();

		if (userService.hasUsage(member)) {
			userService.deleteMember(id);
			return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>(
					"User can not be deleted............. (Member In use -: Books Are not Returned)",
					HttpStatus.NOT_ACCEPTABLE);
		}

	}

}
