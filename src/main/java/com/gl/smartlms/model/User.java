package com.gl.smartlms.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;

//==============================================================
// = User Model
//=============================================================
@Entity
@Table(name = "user")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User() {

	}

	public User(@NotNull String username, @NotNull String password, @NotNull String gender, @NotNull String firstName,String middleName,
	 @NotNull String lastName, @NotNull String dateOfBirth, @NotNull String email,
			@NotNull String contact, @NotNull String type, @NotNull String role, @NotNull Integer active) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.contact = contact;
		this.type = type;
		this.role = role;
		this.active = active;
		this.gender = gender;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "username")
	private String username;

	// @JsonIgnore
	@NotNull
	@Column(name = "password")
	private String password;

	@NotEmpty(message = "*Please enter fisrt name")
	@NotNull(message = "*Please enter fisrt name")
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@NotEmpty(message = "*Please enter last name")
	@NotNull(message = "*Please enter last name")
	@Column(name = "last_name")
	private String lastName;

	@NotEmpty(message = "*Please select gender")
	@NotNull(message = "*Please select gender")
	@Column(name = "gender")
	private String gender;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@NotNull(message = "*Please enter birth date")
	@Column(name = "date_of_birth")
	private String dateOfBirth;

	@CreationTimestamp
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "joining_date")
	private Date joiningDate;

	@UpdateTimestamp
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "last_Modified_Date")
	private Date lastModifiedDate;

	@Column(name = "contact")
	private String contact;

	@Column(name = "email")
	private String email;

	@NotEmpty(message = "*Please select member type")
	@NotNull(message = "please Select Member Type")
	@Column(name = "type")
	private String type;

	@NotNull
	@Column(name = "active")
	private Integer active;

	@NotNull
	@Column(name = "role")
	private String role;

	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Issue> issue;

}
