package com.gl.smartlms.service;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.advice.NoContentFoundException;
import com.gl.smartlms.advice.NoSuchIssueIdFoundException;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Issue;
import com.gl.smartlms.model.User;
import com.gl.smartlms.repository.IssueRepository;

@Service
public class IssueSrviceImpl implements IssueService {

	@Autowired
	private IssueRepository issueRepository;

	@Override
	public Issue IssueBookToMember(Issue issue) {
		issue.setReturned(Constants.BOOK_NOT_RETURNED);
		return issueRepository.save(issue);
	}

	@Override
	public Issue returnBookUpdation(Issue issue) {
		issue.setReturned(Constants.BOOK_RETURNED);
		issue.setReturnDate(new Date());
		return issueRepository.save(issue);
	}

	@Override
	public Optional<Issue> getIssueDetailsById(Long id) {
		
		Optional<Issue> optional= issueRepository.findById(id);
		if(optional.isEmpty()) {
			throw new NoSuchIssueIdFoundException("Issue Id Does not Exist .. There is no Issue Rescord with id " + id);
		}
		return issueRepository.findById(id);
	}



	@Override
	public int compareDates(Date expected_date, Date return_date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String exp = formatter.format(expected_date);
		String ret = formatter.format(return_date);

		try {
			Date edate = formatter.parse(exp);

			Date rdate = formatter.parse(ret);
			return edate.compareTo(rdate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public Issue issueBooks(User member, Book book, Issue issue) {
		Issue issue1 = new Issue();
		issue1.setBook(book);
		issue1.setUser(member);
		issue1.setExpectedDateOfReturn(issue.getExpectedDateOfReturn());
		issue1.setNote(issue.getNote());
		issue1.setReturned(Constants.BOOK_NOT_RETURNED);
		return issueRepository.save(issue1);

	}

	@Override
	public Issue getBookIssueDetails(Book book) {
		return issueRepository.findByBookAndReturned(book,Constants.BOOK_NOT_RETURNED);
	}

	@Override
	public List<Issue> getRecordList() {

		List<Issue> issue = issueRepository.findAll();
		if (issue.isEmpty()) {
			throw new NoContentFoundException("No record Is Present (Books  Are Not issued) List is Empty");
		}

		return issue;
	}

}