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

		User user = userService.getUserValidate(username, password);
		try {

			if (user != null) {
				if (user.getActive() == 1) {
					String userJson = Obj.writeValueAsString(user);

					return new ResponseEntity<String>("User Logged in Succesfully" + userJson, HttpStatus.OK);

				} else {

					return new ResponseEntity<String>("User Not Active", HttpStatus.OK);
				}

			} else {
				return Constants.getResponseEntity("Authentication Failed ...... Invalid Credentials",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.getResponseEntity("Username Not Exist", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// ==============================================================
	// User Register API		(Admin + User)
	// ==============================================================

	@PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
		user = userService.registerUser(user);
		try {
			if (user != null) {
				String userJson = Obj.writeValueAsString(user);
				return new ResponseEntity<String>("User Registered Sucessfully" + userJson, HttpStatus.CREATED);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return Constants.getResponseEntity("Regsitration Failed", HttpStatus.INTERNAL_SERVER_ERROR);

	}

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

	@GetMapping("/faculty/count")
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

	@GetMapping("/student/count")
	public ResponseEntity<String> countAllStudentMembers() {
		Long studentCount = userService.getStudentsCount();
		if (studentCount != 0) {
			return new ResponseEntity<String>(studentCount.toString(), HttpStatus.OK);
		}
		return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// Add Member API				(Admin)
	// ==============================================================
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> saveMember(@Valid @RequestBody User user) {

		user = userService.addNewMember(user);
		if (user != null) {
			return new ResponseEntity<String>(
					"Member Added with Name " + user.getFirstName() + " and type " + user.getType(),
					HttpStatus.CREATED);
		}
		return Constants.getResponseEntity("User Addition Failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// List Users Api				(Admin)
	// ==============================================================
	@GetMapping("/users")
	public ResponseEntity<List<User>> showAllUsers() {

		List<User> list = userService.getAll();
		try {
			if (list != null) {
				return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// List Student Member Api (Admin)
	// ==============================================================
	@GetMapping("/student")
	public ResponseEntity<List<User>> showAllStudents() {

		List<User> list = userService.getAllStudent();
		try {
			if (list != null) {
				return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// List Faculty Member Api		(Admin)
	// ==============================================================
	@GetMapping("/faculty")
	public ResponseEntity<List<User>> showAllFaculties() {

		List<User> list = userService.getAllFaculty();
		try {
			if (list != null) {
				return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// List Active Member Api		(Admin)
	// ==============================================================
	@GetMapping("/active")
	public ResponseEntity<List<User>> showAllActive() {

		List<User> list = userService.getAllActive();
		try {
			if (list != null) {
				return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// List Active Member Api		(Admin)
	// ==============================================================
	@GetMapping("/inactive/list")
	public ResponseEntity<List<User>> showAllInActive() {

		List<User> list = userService.getAllInActive();
		try {
			if (list != null) {
				return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// Blocking Member Api			(Admin)
	// ==============================================================
	@PutMapping("/block/{id}")
	public ResponseEntity<String> blockUser(@PathVariable Long id) {
		User user = userService.getMemberById(id);
		if (user != null && user.getActive() == 1) {
			user.setActive(0);
			userService.save(user);
			return new ResponseEntity<String>("User Blocked", HttpStatus.ACCEPTED);
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// UnBlocking Member Api		(Admin)
	// ==============================================================

	@PutMapping("/unblock/{id}")
	public ResponseEntity<String> unlockUser(@PathVariable Long id) {
		User user = userService.getMemberById(id);
		if (user != null && user.getActive() == 0) {
			user.setActive(1);
			userService.save(user);
			return new ResponseEntity<String>("User UnBlocked", HttpStatus.ACCEPTED);
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// Find Member API(change)		(Admin)
	// ==============================================================

	@GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findUserById(@PathVariable Long id) {
		Optional<User> optional = userService.getMember(id);

		try {
			if (optional.isPresent()) {
				User member = optional.get();
				String memberJson = Obj.writeValueAsString(member);
				return new ResponseEntity<>(memberJson, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// Update Member API		(Admin)
	// ==============================================================

	@PutMapping("/update")
	public ResponseEntity<String> updateMember(@Valid @RequestBody User member) {

		Optional<User> member1 = userService.getMember(member.getId());

		if (member1.isPresent()) {

			userService.save(member);

			return new ResponseEntity<String>("Member Updated With Name " + member1.get().getFirstName(),
					HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<String>("Member Not Found", HttpStatus.NO_CONTENT);

	}

	// ==============================================================
	// Delete Member API		(Admin)
	// ==============================================================
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeUser(@PathVariable Long id) {
		User member = userService.getMemberById(id);
		try {
			if (member != null) {
				if (userService.hasUsage(member)) {
					userService.deleteMember(id);
					return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);

				} else {
					return new ResponseEntity<String>(
							"User can not be deleted............. (Member In use -: Books Are not Returned)",
							HttpStatus.NOT_ACCEPTABLE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
