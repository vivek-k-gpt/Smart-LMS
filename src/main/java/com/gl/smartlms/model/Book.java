package com.gl.smartlms.model;

import java.io.Serializable;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import lombok.Data;






//==============================================================
			// = Book Model
//=============================================================
@Entity
@Data
@Table(name = "book")
public class Book implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull(message = "*Please enter book title")
	@NotBlank(message = "*Please enter book title")
	@Column(name = "title")
	private String title;

	@NotNull(message = "*Please enter book tag")
	@NotBlank(message = "*Please enter book tag")
	@Column(name = "tag")
	private String tag;

	@NotNull(message = "*Please enter book authors")
	@NotBlank(message = "*Please enter book authors")
	@Column(name = "authors")
	private String authors;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "status")
	private Integer status;

	@JsonFormat(pattern = "dd-MM-yyyy")
	@Column(name = "create_date")
	private Date createDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull(message = "*Please select category")
	private Category category;

}
