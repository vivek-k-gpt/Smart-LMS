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
import org.springframework.web.bind.annotation.RestController;
import com.gl.smartlms.advice.RegistrationFailedException;
import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.User;
import com.gl.smartlms.service.UserService;

@RestController
public class UserRestController {

	@Autowired
	private UserService userService;
	
	
	// ==============================================================
	// User Register API (ALL)
	// ==============================================================
	@PostMapping(value = "user/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
		userService.findByUsername(user.getUsername());
		user.setRole(Constants.ROLE_USER);
		Optional<User> user1 = Optional.ofNullable(userService.save(user));
		if (user1.isEmpty()) {
			throw new RegistrationFailedException("Registration Failed");
		}
		return new ResponseEntity<String>("User Registered Sucessfully", HttpStatus.CREATED);
	}
	

	
	// ==============================================================
	// Add Librarian API API (Admin)
	// ==============================================================
	@RequestMapping(value = "api-admin/librarian/register", method = RequestMethod.POST)
	public ResponseEntity<String> saveMember(@Valid @RequestBody User user) {
		userService.findByUsername(user.getUsername());
	
		user.setType(Constants.MEMBER_OTHER);
		user.setRole(Constants.ROLE_LIBRARIAN);
		Optional<User> user1 = Optional.ofNullable(userService.save(user));
		if (user1.isEmpty()) {
			throw new RegistrationFailedException("Registration Failed ......");
		}
		return new ResponseEntity<String>("Librarian Registered Successfully", HttpStatus.CREATED);
	}


	//admin
	// ==============================================================
	// User Count API
	// ==============================================================

	@GetMapping("api-admin/user/count")
	public ResponseEntity<String> countAllUsers() {
		Long userCount = userService.getTotalCount();
			return new ResponseEntity<String>(userCount.toString(), HttpStatus.OK);
		} 
	
	//admin
	// ==============================================================
	// Faculty Member Count API (Admin)
	// ==============================================================
	@GetMapping("api-admin-librarian/user/faculty/count")
	public ResponseEntity<String> countAllFacultyMembers() {
		Long facultyCount = userService.getFacultyCount();
			return new ResponseEntity<String>(facultyCount.toString(), HttpStatus.OK);
		}
	
	//admin
	// ==============================================================
	// Student Member Count API (Admin)
	// ==============================================================
	@GetMapping("api-admin-librarian/user/student/count")
	public ResponseEntity<String> countAllStudentMembers() {
		Long studentCount = userService.getStudentsCount();
			return new ResponseEntity<String>(studentCount.toString(), HttpStatus.OK);
		}


	
	//admin
	// ==============================================================
	// List Users Api(Sorted) (Admin)
	// ==============================================================
	@GetMapping("api-admin/users")
	public ResponseEntity<List<User>> showAllUsers() {
		List<User> list = userService.getAll();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}
	
	//admin
	// ==============================================================
	// List Student Member Api (Admin)
	// ==============================================================
	@GetMapping("api-admin-librarian/students")
	public ResponseEntity<List<User>> showAllStudents() {
		List<User> list = userService.getAllStudent();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}

	//admin
	// ==============================================================
	// List Faculty Member Api (Admin)
	// ==============================================================
	@GetMapping("api-admin-librarian/faculty")
	public ResponseEntity<List<User>> showAllFaculties() {
		List<User> list = userService.getAllFaculty();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}


	//admin+librarian
	// ==============================================================
	// Find Member API(change) (Admin)
	// ==============================================================

	@GetMapping(value = "api-admin-librarian/find/{id}")
	public ResponseEntity<User> findUserById(@PathVariable Long id) {
		User user = userService.getMember(id).get();
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}

	
	//all
	// ==============================================================
	// Update Member API (Admin)
	// ============================================================
	@PutMapping("api-all/update")
	public ResponseEntity<String> updateMember(@Valid @RequestBody User member) {
		Optional<User> member1 = userService.getMember(member.getId());
		userService.update(member,member1.get());
		return new ResponseEntity<String>("Member Updated With Name " + member.getFirstName(),
				HttpStatus.ACCEPTED);
	}

	//admin
	// ==============================================================
	// Delete Member API (Admin)
	// ==============================================================
	
	@RequestMapping(value = "api-admin/delete/{id}", method = RequestMethod.DELETE)
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
