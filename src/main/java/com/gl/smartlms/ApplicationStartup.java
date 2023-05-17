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
	// = onLoad The Admin And LibrarianS
// ==============================================================
	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		initDatabaseEntities();
	}

	// ==============================================================
	// = ADMIN AND LIBRARIAN WILL BE AVAILABLE ON APPLICATION STARTUP
	// ==============================================================
	private void initDatabaseEntities() {

		// ==============================================================
		// = Only When if the User Table Is Empty
		// ==============================================================
		if (userService.getAllUser().size() == 0) {

			// ==============================================================
			// = Registering Admin
			// =================================================================
			userService.save(new User("admin", "admin", "male", "vivek", "kumar", "gupta", "08-08-1999",
					"vivekgp8899@gmail.com", "8527648963", Constants.MEMBER_OTHER, Constants.ROLE_ADMIN));

			// ==============================================================
			// = Registering Librarian
			// =================================================================
			userService.save(new User("librarian", "librarian", "male", "saksham", "", "sharma", "10-03-1999",
					"saksham@gmail.com", "9854352341", Constants.MEMBER_OTHER, Constants.ROLE_LIBRARIAN));

		}

	}
}