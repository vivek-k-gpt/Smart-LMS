package com.gl.smartlms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gl.smartlms.customexception.NoSuchIssueIdFoundException;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Issue;
import com.gl.smartlms.model.User;

@Service
public interface IssueService {

	public Issue IssueBookToMember(Issue issue);

	public Issue returnBookUpdation(Issue issue);

	public Optional<Issue> getIssueDetailsById(Long id);
	
	public Issue getIssueDetail(Long id) throws NoSuchIssueIdFoundException;

	public int compareDates(Date expected_date, Date return_date);

	public Issue issueBooks(User member, Book book, Issue issue);

	public Issue getBookIssueDetails(Book book);

	public List<Issue> getRecordList();

}
