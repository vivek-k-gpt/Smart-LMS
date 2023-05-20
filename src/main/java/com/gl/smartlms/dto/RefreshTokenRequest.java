package com.gl.smartlms.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//====================================================================================
//= Class to Help In Sending RefreshToken Request
//====================================================================================
@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest {

	private String token;
}
