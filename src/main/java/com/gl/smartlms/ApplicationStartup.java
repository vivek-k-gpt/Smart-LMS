package com.gl.smartlms;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.gl.smartlms.service.UserService;

import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.User;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private UserService userService;

	
// ==============================================================
			// = onLoad The Admin And Librarian
// ==============================================================
	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		initDatabaseEntities();
	}

	private void initDatabaseEntities() {

		if (userService.getAll().size() == 0) {
			userService.registerUser(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999",
					"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN, 1));
			userService.registerUser(new User("librarian", "librarian", "male", "saksham", "", "sharma", "10-03-1999",
					"saksham@gmail.com", "9854352341", Constants.MEMBER_OTHER, Constants.ROLE_LIBRARIAN, 1));

		}

	}
}