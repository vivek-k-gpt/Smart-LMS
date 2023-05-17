package com.gl.smartlms.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ==============================================================
// = Class To help in mapping UserName And Password For Generating JWT token
// =================================================================
@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	private String username;
	private String password;
}
