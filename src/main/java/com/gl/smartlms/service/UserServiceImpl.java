package com.gl.smartlms.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.smartlms.advice.NoContentFoundException;
import com.gl.smartlms.advice.UserNotFoundException;
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
		
		
		List<User> users = userRepository.findAllByOrderByFirstNameAscMiddleNameAsc();
		if(users.isEmpty()) {
			throw new NoContentFoundException("No User Is Present  List is Empty");
		}
		return users;

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
		
		Optional<User> user =  userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("No User is found with id :" + id);
			
		}
		return user;
		
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


	@Override
	public List<User> getAllActive() {
	
		List<User>  activeList= userRepository.findByActive(1);
		if(activeList.isEmpty()) {
		throw new NoContentFoundException("No Active User !  List is Empty");
		}
		return activeList;
	}


	@Override
	public List<User> getAllInActive() {
		
		List<User> inactiveList = userRepository.findByActive(0);
		if(inactiveList.isEmpty()) {
			throw new NoContentFoundException("No InActive User !  List is Empty");
			}
			return inactiveList;
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
