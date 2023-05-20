package com.gl.smartlms.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



//====================================================================================
//= Class to Help In Getting  JWTToken Response 
//====================================================================================
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Hidden
public class JwtResponse {

	private String accessToken;
	private String token;
}
