package com.gl.smartlms.model;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

	
	private String username;
	private String password;
}
