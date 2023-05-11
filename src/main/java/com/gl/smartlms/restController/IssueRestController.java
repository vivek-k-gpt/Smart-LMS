package com.gl.smartlms.restController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.User;
import com.gl.smartlms.service.BookService;
import com.gl.smartlms.service.IssueService;
import com.gl.smartlms.service.UserService;
import com.gl.smartlms.model.Issue;

@RestController
public class IssueRestController {

	@Autowired
	private BookService bookService;
	@Autowired
	private UserService userService;
	@Autowired
	private IssueService issueService;

	
	
	
	
	// ==============================================================
	// Issue Book Api 
	// ==============================================================
	@PostMapping("api-librarian/issue/book")
	public ResponseEntity<String> issueBook(@RequestBody Issue issue) {
		Book book = bookService.getBookById(issue.getBook().getId()).get();
		User member = userService.getMember(issue.getUser().getId()).get();
		if (book.getStatus() == Constants.BOOK_STATUS_AVAILABLE) {
			book.setStatus(Constants.BOOK_STATUS_ISSUED);
			bookService.saveBook(book);
			issue.setBook(book);
			issue.setUser(member);
		Issue issue1 =	issueService.IssueBookToMember(issue);
			List<Issue> iss = new ArrayList<>();
			iss.add(issue);
			member.setIssue(iss);
			userService.save(member);
			return new ResponseEntity<String>("BookIssued to Member " + member.getId(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Book Is Already issued to Another Member" , HttpStatus.NOT_ACCEPTABLE);
	}
		
	}

	
	
	// ==============================================================
	// Issue Books Api(Issue Mulliple books to User)
	// ==============================================================
	@PostMapping("api-librarian/issue/books/{ids}")
	public ResponseEntity<String> issueBooks(@PathVariable("ids") List<Long> ids, @RequestBody Issue issue) {
		User member = userService.getMember(issue.getUser().getId()).get();
		List<Issue> issuedList = member.getIssue();
		String s = "";
		String s2 = "";
		for (Long i : ids) {
			Book book = bookService.getBookById(i).get();
			if (book.getStatus() == Constants.BOOK_STATUS_AVAILABLE) {
				book.setStatus(Constants.BOOK_STATUS_ISSUED);
				bookService.saveBook(book);
				Issue issue1 = issueService.issueBooks(member, book, issue);
				issuedList.add(issue1);
				String si = i.toString();
				s += (si + " ");
			} else {
				String si = i.toString();
				s2 += (si + " ");
			}
		}

		member.setIssue(issuedList);
		userService.save(member);
		if (s2.isEmpty()) {
			return new ResponseEntity<String>("BOOKS ARE ISSUED SUCCESSFULLY TO THE USER Having Book ids " + s,
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("BOOKS ARE ISSUED SUCCESSFULLY TO THE USER Having Book ids " + s
				+ "Books are already issued to another members having book id" + s2, HttpStatus.OK);
	}

	
	
	
	// ==============================================================
	// Return Book Api (Admin)
	// ==============================================================
	@PutMapping("api-librarian/return/book")
	public ResponseEntity<String> returnBook(@RequestParam("book_id") Long id) {
		Book book = bookService.getBookById(id).get();
		if (book.getStatus() == Constants.BOOK_STATUS_ISSUED)

		{
			Issue issue = issueService.getBookIssueDetails(book);
			book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
			bookService.saveBook(book);
			User member = userService.getMember(issue.getUser().getId()).get();
		Issue issue1 =	issueService.returnBookUpdation(issue);
			member.getIssue().remove(issue1);
			userService.save(member);
		
			
			return new ResponseEntity<String>("Book Returned Successfully", HttpStatus.ACCEPTED);
		}

		return new ResponseEntity<String>("Book is not issued", HttpStatus.OK);

	}



	
	
	
	
	
	// ==============================================================
	// Return Book Api (Multiple books return)
	// ==============================================================
	@PutMapping("api-librarian/return/books/{user_id}/{book_ids}")
	public ResponseEntity<String> returnBooks(@PathVariable("user_id") Long id, @PathVariable List<Long> book_ids) {

		User user = userService.getMember(id).get();
		for (Long b_id : book_ids) {
			Book book = bookService.getBookById(b_id).get();
			if (book.getStatus() == Constants.BOOK_STATUS_ISSUED) {
				book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
			}
			Issue issue = issueService.getBookIssueDetails(book);
			issue.getUser().getIssue().remove(issue);
			user.getIssue().remove(issue);

			bookService.saveBook(book);
			issueService.returnBookUpdation(issue);
		}
		userService.save(user);
		return new ResponseEntity<String>("Books Returned Successfully", HttpStatus.OK);
	}

	
	
	// ==============================================================
	// Issue Records Api 
	// ==============================================================
	@GetMapping("api-librarian/issue/record")
	public ResponseEntity<List<Issue>> getRecord() {
		List<Issue> recordList = issueService.getRecordList();
		return new ResponseEntity<List<Issue>>(recordList, HttpStatus.OK);
	}
	
	
	
	

	
	// ==============================================================
	// Get Books Isuued to A Member
	// ==============================================================
	@GetMapping("api-librarian/book/issue-list/{member_id}")
	public ResponseEntity<List<Issue>> getIssuedBookOfMember(@PathVariable("member_id") Long Id) {
		User member = userService.getMember(Id).get();
		List<Issue> issue = issueService.getIssueByMember(member);
		return new ResponseEntity<List<Issue>>(issue, HttpStatus.OK);
	}
	
	

	// ==============================================================
	// Check Fine Status Api 
	// ==============================================================
	@GetMapping("api-librarian/fine")
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