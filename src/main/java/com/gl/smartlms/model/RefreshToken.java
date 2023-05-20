package com.gl.smartlms.model;

import java.time.Instant;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



//====================================================================================
//= RefreshToken Model
//====================================================================================
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Hidden
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String token;

	private Instant expiryDate;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User userInfo;
}
