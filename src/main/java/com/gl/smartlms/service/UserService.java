package com.gl.smartlms.service;

import java.util.List;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.gl.smartlms.model.User;

//==============================================================
//= UserService  interface 
//=================================================================
@Service
public interface UserService {

	public List<User> getAll();

	public Optional<User> getMember(Long id);

	public Long getTotalCount();

	public Long getFacultyCount();

	public Long getStudentsCount();

	

	public User save(User user);

	public List<User> getAllStudent();

	public List<User> getAllFaculty();

	public void deleteMember(Long id);

	public List<User> getAllUser();

	public void findByUsername(String username);

	public void update(User member, User user);

	public User saveLibrarian(User user);

	public User saveUser(@Valid User user);

}
