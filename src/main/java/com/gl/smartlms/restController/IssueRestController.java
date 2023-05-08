package com.gl.smartlms.restController;

import java.util.ArrayList;





import java.util.Date;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.advice.NoSuchIssueIdFoundException;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.User;
import com.gl.smartlms.service.BookService;
import com.gl.smartlms.service.IssueService;
import com.gl.smartlms.service.UserService;
import com.gl.smartlms.model.Issue;

@RestController
@RequestMapping("/api-issue")
public class IssueRestController {

	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private IssueService issueService;

	ObjectMapper Obj = new ObjectMapper();

	
	
	
	
// ==============================================================
		// Issue Book Api	(Admin)
// ==============================================================
		@PostMapping("/save")
		public ResponseEntity<String> issueBook(@RequestBody Issue issue) {

			Book book = bookService.getBookById(issue.getBook().getId()).get();
			User member = userService.getMember(issue.getUser().getId()).get();
				if (book.getStatus() == Constants.BOOK_STATUS_AVAILABLE) {
					book.setStatus(Constants.BOOK_STATUS_ISSUED);
					issue.setBook(book);
					issue.setUser(member);

					bookService.saveBook(book);

					Issue issueDetail = issueService.IssueBookToMember(issue);
					List<Issue> issue1 = new ArrayList<Issue>();
					issue1.add(issueDetail);

					member.setIssue(issue1);
					userService.save(member);
				}
				return new ResponseEntity<String>("BookIssued to Member " + member.getId(), HttpStatus.OK);
				
		} 
		
		
		// ==============================================================
				// Issue Books Api(Issue Mulliple books to User)	(Admin)
		// ==============================================================

		@PostMapping("/save/{ids}")
		public ResponseEntity<String> issueBooks(@PathVariable("ids") List<Long> ids, @RequestBody Issue issue) {
			User member = userService.getMember(issue.getUser().getId()).get();
			List<Issue> issuedList = new ArrayList<>();
			for (Long i : ids) {
				Book book = bookService.getBookById(i).get();
				if (book.getStatus() == Constants.BOOK_STATUS_AVAILABLE) {
					book.setStatus(Constants.BOOK_STATUS_ISSUED);
					bookService.saveBook(book);
					Issue issue1 = issueService.issueBooks(member, book, issue);
					issuedList.add(issue1);
				}
			}

			member.setIssue(issuedList);
			userService.save(member);
			return new ResponseEntity<String>("BOOKS ARE ISSUED SUCCESSFULLY TO THE USER", HttpStatus.OK);
		}
	
	
// ==============================================================
		// Return Book Api	(Admin)
// ==============================================================
	@PutMapping("/return")
	public ResponseEntity<String> returnBook(@RequestParam ("issue_id") Long id) {

		Issue issue = issueService.getIssueDetail(id);
		try {
			if (issue != null) {
				Book book = issue.getBook();
				if (book.getStatus() == Constants.BOOK_STATUS_ISSUED) {
					book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
					issue.setBook(book);
					bookService.saveBook(book);
					User member = userService.getMember(issue.getUser().getId()).get();
					member.getIssue().remove(issue);
					userService.save(member);
			 	issueService.returnBookUpdation(issue);
					
					return new ResponseEntity<String>("Book Returned Successfully", HttpStatus.ACCEPTED);
				}
			} else {

				return new ResponseEntity<String>("Issue Id Does not exist", HttpStatus.OK);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	// ==============================================================
			// Return Book Api (Multiple books return)	(Admin)
	// ==============================================================
	@PutMapping("/return/books/{user_id}/{book_ids}")
	public ResponseEntity<String> returnBooks(@PathVariable("user_id") Long id, @PathVariable List<Long> book_ids) {

		User user = userService.getMember(id).get();
		for (Long b_id : book_ids) {
			Book book = bookService.getBookById(b_id).get();
			book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
			Issue issue = issueService.getBookIssueDetails(book);
			user.getIssue().remove(issue);
			bookService.saveBook(book);
			issueService.returnBookUpdation(issue);
		}
		userService.save(user);
		return new ResponseEntity<String>("Books Returned Successfully", HttpStatus.OK);
	}
	
	
	
	
	// ==============================================================
				// Issue Records  Api 	(Admin)
	// ==============================================================
	@GetMapping("/record")
	public ResponseEntity<List<Issue>> getRecord() {
		List<Issue> recordList = issueService.getRecordList();
		return new ResponseEntity<List<Issue>>(recordList, HttpStatus.OK);
	}
	

	
	
	// ==============================================================
			// Check Fine Status Api 	(Admin)
		// ==============================================================
		@GetMapping("/fine")
		public ResponseEntity<String> checkFineStatus(@RequestParam("issue_id") Long id) {
			Issue issue = issueService.getIssueDetailsById(id).get();
			String msg = "";
			if (issue.getReturned() == Constants.BOOK_RETURNED) {
				Date expected_date = issue.getExpectedDateOfReturn();
				Date return_date = issue.getReturnDate();
				int result = issueService.compareDates(expected_date, return_date);
				if (result < 0) {
					msg = "fine applicable";
				} else {
					msg = "no fine Applicable";
				}
			} else {

				return new ResponseEntity<String>("Book is not returned yet", HttpStatus.OK);
			}

			return new ResponseEntity<String>(msg, HttpStatus.OK);

		}
		
	
		
		
		
}