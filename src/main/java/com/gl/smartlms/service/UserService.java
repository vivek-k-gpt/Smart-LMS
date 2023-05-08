package com.gl.smartlms.service;


import java.util.List;


import java.util.Optional;



import org.springframework.stereotype.Service;

import com.gl.smartlms.model.User;




@Service
public interface UserService {

 
	public List<User> getAll();

	public User addNewMember(User member);
	
	public User save(User user);
	
	public Optional<User> getMember(Long id);

	public Long getTotalCount();

	public Long getFacultyCount();
	
	public Long getStudentsCount();
	
	 
	public User getMemberById(Long id);

	public User getUserValidate(String username, String password);

	public User registerUser(User user);

	public List<User> getAllStudent();

	public List<User> getAllFaculty();

	public List<User> getAllActive();

	public List<User> getAllInActive();

	public boolean hasUsage(User member);

	public void deleteMember(Long id);

	



	
}
