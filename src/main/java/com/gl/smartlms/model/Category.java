package com.gl.smartlms.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//==============================================================
// = Category Model
//=============================================================
@Entity
@Table(name = "category")
@Data
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull(message = "*Please enter category name")
	@NotBlank(message = "*Please enter category name")
	@Column(name = "name")
	private String name;

	@NotNull(message = "*Please enter category short name")
	@NotBlank(message = "*Please enter category short name")
	@Size(max = 4, message = "*Must not exceed 4 characters.")
	@Column(name = "short_name")
	private String shortName;

	@Column(name = "notes")
	@Size(max = 1000, message = "*Must not exceed 1000 characters.")
	private String notes;
	
	@CreationTimestamp
	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "create_date")
	private Date createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Book> books;
}
