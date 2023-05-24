package com.gl.smartlms.serviceTest;

import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;

import com.gl.smartlms.repository.IssueRepository;

import com.gl.smartlms.service.IssueSrviceImpl;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Issue;
import com.gl.smartlms.model.User;

@SpringBootTest(classes = { IssueServiceTest.class })
public class IssueServiceTest {

	
	
	
	 @Mock
	    private IssueRepository repository;
	    
	    @InjectMocks
	    private IssueSrviceImpl service;
	    
	    @Test
	    public void issueBookTest()
	    {
	        Issue issue=buildIssue();
	        when(repository.save(issue)).thenReturn(issue);
	        assertEquals(issue, service.IssueBookToMember(issue));
	        
	    }
	    
	    @Test
	    public void returnBookTest()
	    {
	        Issue issue=buildIssue();
	        when(repository.save(issue)).thenReturn(issue);
	        assertEquals(issue, service.returnBookUpdation(issue));
	        
	    }
	    
	    @Test
	    public void issueBooksTest()
	    {
	        Issue issue=buildIssue();
	        when(repository.save(issue)).thenReturn(issue);
	        assertEquals(issue, service.issueBooks(issue.getUser(), issue.getBook(), issue));
	    }

	    @Test
	    public void returnBooksTest()
	    {
	        Issue issue=buildIssue();
	        when(repository.save(issue)).thenReturn(issue);
	        assertEquals(issue, service.returnBookUpdation(issue));
	        
	    }
	    
	    
	    public Issue buildIssue()
	    {
	        User user= new User();
	        user.setId(101l);
	        Book book= new Book();
	        book.setId(102l);
	        
	        Issue issue= new Issue();
	        issue.setBook(book);
	        issue.setUser(user);
	        issue.setNote("This is my book");
	        return issue;
	    }
}
