package com.gl.smartlms.model;

import java.io.Serializable;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

//==============================================================
// = Issue Model
//=============================================================
@Entity
@Table(name = "issue")
@Data
public class Issue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@CreationTimestamp
	@JsonFormat(pattern = "dd-MM-yyyy")
	@CreatedDate
	@Column(name = "issue_date")
	private Date createdDate;

	@Column(name = "notes")
	private String note;

	@Column(name = "returned")
	private int returned;


	@JsonFormat(pattern = "dd-MM-yyyy")
	@CreatedDate
	@Column(name = "expectedte_date_of_return")
	private Date expectedDateOfReturn;

	
	
	@JsonFormat(pattern = "dd-MM-yyyy")
	@CreatedDate
	@Column(name = "return_date")
	private Date returnDate;

	@OneToOne
	@JoinColumn(name = "Book_id")
	private Book book;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
