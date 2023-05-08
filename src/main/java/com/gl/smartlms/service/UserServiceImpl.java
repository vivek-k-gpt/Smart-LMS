package com.gl.smartlms.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.User;
import com.gl.smartlms.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

@Autowired
private UserRepository userRepository;



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
		return userRepository.findAllByOrderByFirstNameAscMiddleNameAsc();

	}

	@Override
	public User addNewMember(User member) {
		member.setJoiningDate( new Date() );
		return userRepository.save( member );
	}

	@Override
	public User save(User member) {
		
		return userRepository.save(member);
	}

	@Override
	public Optional<User> getMember(Long id) {
		
		return userRepository.findById(id);
	}


	@Override
	public User getMemberById(Long id) {

		return userRepository.findById(id).get();
	}


	@Override
	public User getUserValidate(String username, String password) {
		
		return userRepository.findByUsernameAndPassword(username,password);
	}


	@Override
	public User registerUser(User user) {

		return userRepository.save(user);
	}


	@Override
	public List<User> getAllStudent() {
		
		return userRepository.findByTypeContaining(Constants.MEMBER_STUDENT);
	}


	@Override
	public List<User> getAllFaculty() {
	
		return userRepository.findByTypeContaining(Constants.MEMBER_FACULTY);
	}


	@Override
	public List<User> getAllActive() {
	
		return userRepository.findByActive(1);
	}


	@Override
	public List<User> getAllInActive() {
		
		return  userRepository.findByActive(0);
	}


	@Override
	public boolean hasUsage(User member) {
	
		return member.getIssue().size() == 0;
	}


	@Override
	public void deleteMember(Long id) {
	
		userRepository.deleteById(id);
	}





}
