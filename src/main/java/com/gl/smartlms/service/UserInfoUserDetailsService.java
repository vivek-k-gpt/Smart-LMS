package com.gl.smartlms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gl.smartlms.model.User;
import com.gl.smartlms.model.UserDetail;
import com.gl.smartlms.repository.UserRepository;


@Component
public class UserInfoUserDetailsService  implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = Optional.ofNullable((userRepository.findByUsername(username)));
		 return user.map(UserDetail::new).orElseThrow(()->new UsernameNotFoundException("user not found " + username));

	}

}
