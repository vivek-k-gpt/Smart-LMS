package com.gl.smartlms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Issue;
import com.gl.smartlms.model.User;

@Repository 
public interface IssueRepository extends JpaRepository<Issue, Long> {

	Issue findByBookAndReturned(Book book, Integer bookNotReturned);

	List<Issue> findByUserAndReturned(User member, Integer bookNotReturned);




	
	

}
