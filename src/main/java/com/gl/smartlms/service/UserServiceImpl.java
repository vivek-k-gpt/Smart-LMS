package com.gl.smartlms.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gl.smartlms.advice.NoContentFoundException;
import com.gl.smartlms.advice.UserNameNotFoundException;
import com.gl.smartlms.advice.UserNotFoundException;
import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.User;
import com.gl.smartlms.repository.IssueRepository;
import com.gl.smartlms.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired(required = true)
	private BCryptPasswordEncoder beBCryptPasswordEncoder;
	
	

	public Long getTotalCount() {
		return userRepository.count();
	}

	public Long getFacultyCount() {
		return userRepository.countByType(Constants.MEMBER_FACULTY);
	}

	public Long getStudentsCount() {
		return userRepository.countByType(Constants.MEMBER_STUDENT);
	}

	@Override
	public List<User> getAll() {

		List<User> users = userRepository.findAllByOrderByFirstNameAscMiddleNameAsc();
		if (users.isEmpty()) {
			throw new NoContentFoundException("No User Is Present  List is Empty");
		}
		return users;

	}

	@Override
	public Optional<User> getMember(Long id) {

		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("No User is found with id :" + id);

		}
		return user;

	}

	@Override
	public User getUserValidate(String username, String password) {

		return userRepository.findByUsernameAndPassword(username, beBCryptPasswordEncoder.encode(password));
	}

	@Override
	public User save(User user) {

		user.setPassword(beBCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllStudent() {

		List<User> students = userRepository.findByTypeContaining(Constants.MEMBER_STUDENT);

		if (students.isEmpty()) {
			throw new NoContentFoundException("No Student is present List is Empty");
		}
		return students;

	}

	@Override
	public List<User> getAllFaculty() {

		List<User> facultylist = userRepository.findByTypeContaining(Constants.MEMBER_FACULTY);

		if (facultylist.isEmpty()) {
			throw new NoContentFoundException("No Faculty is present List is Empty");
		}
		return facultylist;
	}

//	@Override
//	public boolean hasUsage(User member) {
//
//		return member.getIssue().size() == 0;
//	}

	@Override
	public void deleteMember(Long id) {

		userRepository.deleteById(id);
	}

	@Override
	public List<User> getAllUser() {

		return userRepository.findAll();
	}

	@Override
	public void findByUsername(String username) {

		Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
		if (user.isPresent()) {
			throw new UserNameNotFoundException("Username is already exist " + username);

		}

	}

	@Override
	public void update(User member, User user) {
		member.setPassword(user.getPassword());
		member.setRole(user.getRole());
		member.setUsername(user.getUsername());
		member.setType(user.getType());
		member.setJoiningDate(user.getJoiningDate());
		userRepository.save(member);

	}

	@Override
	public User saveLibrarian(User user) {
		user.setType(Constants.MEMBER_OTHER);
		user.setRole(Constants.ROLE_LIBRARIAN);
		user.setPassword(beBCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User saveUser(@Valid User user) {
		user.setRole(Constants.ROLE_USER);
		user.setPassword(beBCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

}
