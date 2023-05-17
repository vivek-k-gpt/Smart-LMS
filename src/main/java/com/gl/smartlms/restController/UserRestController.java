package com.gl.smartlms.restController;
import java.util.List;

import org.springframework.security.core.Authentication;
import java.util.Optional;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.gl.smartlms.advice.RegistrationFailedException;
import com.gl.smartlms.model.AuthRequest;
import com.gl.smartlms.model.User;
import com.gl.smartlms.service.IssueService;
import com.gl.smartlms.service.JwtService;
import com.gl.smartlms.service.UserService;
import com.mysql.cj.protocol.AuthenticationProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.gl.smartlms.advice.UserNameNotFoundException;

@RestController
//@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User")
public class UserRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	//logging 
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	
	
	
	// ==============================================================
	// JWT TOKEN generator
	// ==============================================================
	@Operation(description = "Post End-Point for Generating JWT Token", summary = "API For Generating TOKEN", responses = {
			@ApiResponse(description = "Success", responseCode = "200"),
			@ApiResponse(description = "User Not Found", responseCode = "403")

	})
	@PostMapping("/user/authenticate")
	public String authentucateAndGetToken(@RequestBody AuthRequest authRequest) throws UserNameNotFoundException{
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
       } 
            
            else {
        	System.out.println("username is not available");
            throw new UserNameNotFoundException("invalid user request !");
        }
		
	}
	
	
	// ==============================================================
	// User Register API (ALL) role - user
	// ==============================================================
	@Operation(description = "Post End-Point for Registering User", summary = "API For Registering User")
	@PostMapping(value = "user/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
		userService.findByUsername(user.getUsername());
		logger.info("creating An User With type Student Or Faculty");
		Optional<User> user1 = Optional.ofNullable(userService.saveUser(user));
		if (user1.isEmpty()) {
			throw new RegistrationFailedException("Registration Failed");
		}
		return new ResponseEntity<String>("User Registered Sucessfully", HttpStatus.CREATED);
	}
	

	
	// ==============================================================
	// Add Librarian API API 
	// ==============================================================
	@Operation(description = "Post End-Point for Registering Librarian 'Only Admin Can Add The Libraian'", summary = "API For Registering Librarian")
	@RequestMapping(value = "api-admin/librarian/register", method = RequestMethod.POST)
	public ResponseEntity<String> saveMember(@Valid @RequestBody User user) {
		userService.findByUsername(user.getUsername());
		
		Optional<User> user1 = Optional.ofNullable(userService.saveLibrarian(user));
		if (user1.isEmpty()) {
			throw new RegistrationFailedException("Registration Failed ......");
		}
		return new ResponseEntity<String>("Librarian Registered Successfully", HttpStatus.CREATED);
	}


	
	
	// ==============================================================
	// User Count API
	// ==============================================================
	@Operation(description = "Get End-Point for Counting Total User", summary = "API For getting User Count")
	@GetMapping("api-admin/user/count")
	public ResponseEntity<String> countAllUsers() {
		Long userCount = userService.getTotalCount();
			return new ResponseEntity<String>(userCount.toString(), HttpStatus.OK);
		} 

	
	
	// ==============================================================
	// Faculty Member Count API 
	// ==============================================================
	@Operation(description = "Get End-Point for Counting Faculty Member", summary = "API For getting Faculty Count")
	@GetMapping("api-admin-librarian/user/faculty/count")
	public ResponseEntity<String> countAllFacultyMembers() {
		Long facultyCount = userService.getFacultyCount();
			return new ResponseEntity<String>(facultyCount.toString(), HttpStatus.OK);
		}
	

	
	
	// ==============================================================
	// Student Member Count API 
	// ==============================================================
	@Operation(description = "Get End-Point for Counting Student Member", summary = "API For getting Student Count")
	@GetMapping("api-admin-librarian/user/student/count")
	public ResponseEntity<String> countAllStudentMembers() {
		Long studentCount = userService.getStudentsCount();
			return new ResponseEntity<String>(studentCount.toString(), HttpStatus.OK);
		}


	

	
	
	// ==============================================================
	// List Users Api(Sorted) 
	// ==============================================================
	@Operation(description = "Get End-Point for Listing All User", summary = "API For getting Users")
	@GetMapping("api-admin/users")
	public ResponseEntity<List<User>> showAllUsers() {
		List<User> list = userService.getAll();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}
	

	
	
	// ==============================================================
	// List Student Member Api 
	// ==============================================================
	@Operation(description = "Get End-Point for Listing All Student", summary = "API For getting Students")
	@GetMapping("api-admin-librarian/students")
	public ResponseEntity<List<User>> showAllStudents() {
		List<User> list = userService.getAllStudent();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}


	
	
	
	// ==============================================================
	// List Faculty Member Api 
	// ==============================================================
	@Operation(description = "Get End-Point for Listing All Faculty", summary = "API For getting Faculties")
	@GetMapping("api-admin-librarian/faculty")
	public ResponseEntity<List<User>> showAllFaculties() {
		List<User> list = userService.getAllFaculty();
		return new ResponseEntity<List<User>>(list, HttpStatus.FOUND);
	}


	
	
	// ==============================================================
	// Find Member API
	// ==============================================================
	@Operation(description = "Get End-Point for Finding User By Id", summary = "API For Searching User")
	@GetMapping(value = "api-admin-librarian/find/{user_id}")
	public ResponseEntity<User> findUserById(@PathVariable  ("user_id") Long id) {
		User user = userService.getMember(id).get();
		return new ResponseEntity<User>(user, HttpStatus.FOUND);
	}

	
	
	
	// ==============================================================
	// Update Member API 
	// ============================================================
	@Operation(description = "Put End-Point for Editing User Details", summary = "API For Updating User")
	@PutMapping("api-all/update")
	public ResponseEntity<String> updateMember(@Valid @RequestBody User member) {
		Optional<User> member1 = userService.getMember(member.getId());
		userService.update(member,member1.get());
		return new ResponseEntity<String>("Member Updated With Name " + member.getFirstName(),
				HttpStatus.ACCEPTED);
	}


	
	
	
	// ==============================================================
	// Delete Member API 
	// ==============================================================
	@Operation(description = "Delete End-Point for Deleting User", summary = "API For Deleting User")
	@RequestMapping(value = "api-admin/delete/{user_id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeUser(@PathVariable ("user_id") Long id) {
		User member = userService.getMember(id).get();
		if (issueService.hasUsage(member)) {
			userService.deleteMember(id);
			return new ResponseEntity<String>("User Deleted Successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(
					"User can not be deleted............. (Member In use -: Books Are not Returned)",
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
