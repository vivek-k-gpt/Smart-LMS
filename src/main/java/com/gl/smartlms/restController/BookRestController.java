package com.gl.smartlms.restController;

import org.springframework.http.HttpStatus;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



import com.gl.smartlms.advice.BookNotFoundException;
import com.gl.smartlms.advice.BookTagAlreadyExistException;
import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Category;

import com.gl.smartlms.service.BookService;
import com.gl.smartlms.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api-book")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CategoryService categoryService;

// ==============================================================//
	// Create//
// ==============================================================//

	// ==============================================================
	// Add Category Api (Admin)
	// ==============================================================
	@PostMapping("/add/{id}")
	public ResponseEntity<String> addBook(@RequestBody Book book, @PathVariable("id") Long id) {
		Category category = categoryService.getCategory(id).get();
		book.setCategory(category);
		Book book1 = bookService.getByTag(book.getTag());
		if (book1 != null) {
			throw new BookTagAlreadyExistException("Tag is Already Present .....Please Select another Tag");
		} else {
			bookService.addNewBook(book);
			return new ResponseEntity<String>(
					"Book get Added with Title " + book.getTitle() + " and Category " + category.getName(),
					HttpStatus.CREATED);
		}
	}

// ==============================================================
//						 Count
// ==============================================================

	// ==============================================================
	// Count Total Book Api (Admin)
	// ==============================================================
	@GetMapping(value = "/total/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> countAllBooks() {
		Long bookCount = bookService.getTotalCount();
			return new ResponseEntity<String>(bookCount.toString(), HttpStatus.OK);
		}


	// ==============================================================
	// Count Available Book Api (Admin)
	// ==============================================================
	@GetMapping(value = "/available/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> countAllAvaialbleBooks() {
		Long bookCount = bookService.getAvailableBookCount();
			return new ResponseEntity<String>(bookCount.toString(), HttpStatus.OK);
		}

	// ==============================================================
	// Count Issued Book Api (Admin)
	// ==============================================================
	@GetMapping(value = "/issued/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> countAllIssuedBooks() {
		Long bookCount = bookService.getIssuedBookCount();
			return new ResponseEntity<String>(bookCount.toString(), HttpStatus.OK);
		}
		

// ==============================================================
	// LIST (Book Based Filtering)
// ==============================================================

	// ==============================================================
	// List All Book Api (Admin + User)
	// ==============================================================
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/list")
	public ResponseEntity<List<Book>> showAllBooks() {
		List<Book> list = bookService.getAll();
		return new ResponseEntity<List<Book>>(list, HttpStatus.FOUND);
	}

	// ==============================================================
	// List All Book(By Title) Api (Admin + User)
	// ==============================================================
	@GetMapping("/list-by-title/{title}")
	public ResponseEntity<List<Book>> getBytitle(@PathVariable String title) {
		List<Book> list = bookService.getBookWithTitle(title);
		return new ResponseEntity<List<Book>>(list, HttpStatus.FOUND);
	}

	// ==============================================================
	// List All Book(By Tagname) Api (Admin + User)
	// ==============================================================
	@GetMapping("/find/{tag}")
	public ResponseEntity<Book> findBookBytag(@PathVariable String tag) {
		Book book = bookService.getByTag(tag);
		if (book != null) {
			return new ResponseEntity<Book>(book, HttpStatus.OK);
		}
		throw new BookNotFoundException("No book is found with tag " + tag);
	}

	// ==============================================================
	// List All Book(By Authors) Api (Admin + User)
	// ==============================================================
	@GetMapping(value = "/list-by-author/{authors}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable("authors") String authors) {
		List<Book> book = bookService.getByAuthorName(authors);
		return new ResponseEntity<List<Book>>(book, HttpStatus.FOUND);
	}

	// ==============================================================
	// List All Book(By publisher) Api (Admin + User)
	// ==============================================================
	@GetMapping(value = "/list-by-publisher/{publisher}")
	public ResponseEntity<List<Book>> getBooksByPublisher(@PathVariable String publisher) {
		List<Book> book = bookService.getBypublisherName(publisher);
		return new ResponseEntity<List<Book>>(book, HttpStatus.FOUND);
	}

// ==============================================================
	// List All Available Books Api (Admin + User)
// ==============================================================
	@GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Book>> getAllAvailableBooks() {
		List<Book> availableBooks = bookService.checkAvailableBooks();
		return new ResponseEntity<List<Book>>(availableBooks, HttpStatus.OK);
	}

// ==============================================================
	// List All Issued Books Api (Admin )
// ==============================================================
	@GetMapping("/issued-books")
	public ResponseEntity<List<Book>> getAllIssuedBooks() {
		List<Book> issuedBooks = bookService.checkIssuedBooks();
		return new ResponseEntity<List<Book>>(issuedBooks, HttpStatus.OK);
	}

//==============================================================
//				LIST CAtegory Based Filtering
//==============================================================

//==============================================================
// List All Books In A category (By Category Name)
//==============================================================	
	@GetMapping(value = "/category/all-books/{category_name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Book>> getAvailableBooksInCategory(@PathVariable("category_name") String name) {
		Category category1 = categoryService.getCategoryByName(name).get();
		List<Book> list = bookService.geAvailabletByCategory(category1);
		return new ResponseEntity<List<Book>>(list, HttpStatus.FOUND);
	}

//==============================================================
//List All Books In A category (By Category Id)
//==============================================================		
	@GetMapping(value = "/category/all/{category_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Book>> findBooksByCategory(@PathVariable("category_id") Long id) {

		Category category = categoryService.getCategory(id).get();
		List<Book> list = bookService.getByCategory(category);
		return new ResponseEntity<List<Book>>(list, HttpStatus.FOUND);
	}

//==============================================================
//List All Issued Books In A category (By Category Name)
//==============================================================
	@GetMapping(value = "/category/issued/all/{type}")
	public ResponseEntity<List<Book>> getAllIssuedBooksInCategory(@PathVariable String type) {
		Category category = categoryService.getCategoryByName(type).get();
		List<Book> issuedBooks = bookService.listCategoryIssuedBooks(category);
		return new ResponseEntity<List<Book>>(issuedBooks, HttpStatus.OK);
	}

//==============================================================
//List All Available Books(Not Issued) In A category (By Category Name)
//==============================================================
	@GetMapping(value = "/category/available/all/{type}")
	public ResponseEntity<List<Book>> getAllAvailableBooksInCategory(@PathVariable String type) {
		Category category = categoryService.getCategoryByName(type).get();
		List<Book> availableBooks = bookService.listCategoryAvailableBooks(category);
		return new ResponseEntity<List<Book>>(availableBooks, HttpStatus.OK);
	}

//==============================================================
//						FIND
//==============================================================

// ==============================================================
// find All Book(By id) Api (Admin + User)
// ==============================================================
	@GetMapping(value = "find/{id}")
	public ResponseEntity<Book> findBookById(@PathVariable Long id) {
		Book book = bookService.getBookById(id).get();
		return new ResponseEntity<Book>(book, HttpStatus.FOUND);
	}

//==============================================================
//find All Books  By List of Book ids (Admin + User)
//==============================================================
	@GetMapping(value = "/find-books/{ids}")
	public ResponseEntity<List<Book>> findBooksWithIdList(@PathVariable List<Long> ids) {
		List<Book> list = bookService.getBooksByIdList(ids);
		return new ResponseEntity<List<Book>>(list, HttpStatus.FOUND);
	}

//==============================================================
//			Update
//==============================================================

//==============================================================
//	Update Book Details (SAMECategory) (Admin)
//==============================================================	
	@PutMapping("/update")
	public ResponseEntity<String> updateBook(@Valid @RequestBody Book book) {
		Book book1 = bookService.getBookById(book.getId()).get();

		Optional<Category> category = categoryService.getCategory(book.getCategory().getId());

		book.setCategory(category.get());
		book.setCreateDate(book1.getCreateDate());
		book.setStatus(book1.getStatus());
		bookService.saveBook(book);
		return new ResponseEntity<String>("Succesfully Updated Book Details", HttpStatus.ACCEPTED);
	}

//==============================================================
//Update Book Details (SAMECategory) (Admin)
//==============================================================
	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateBook(@RequestBody Book book, @PathVariable Long id) {

		Book book1 = bookService.getBookById(book.getId()).get();

		Category category = categoryService.getCategory(id).get();

		Category oldcategory = book1.getCategory();

		oldcategory.getBooks().remove(book1);

		book.setCategory(category);
		book.setCreateDate(book1.getCreateDate());
		book.setStatus(book1.getStatus());

		bookService.saveBook(book);
		return new ResponseEntity<String>("Succesfully Updated Book Details category of type" + category.getName(),
				HttpStatus.ACCEPTED);
	}

//==============================================================
//	Delete
//==============================================================

//==============================================================
//Delete Book By Id (Admin)
//==============================================================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id) {
		Book book = bookService.getBookById(id).get();

		if (book.getStatus() == Constants.BOOK_STATUS_ISSUED) {

			return new ResponseEntity<String>("Book in use Can net be delete (book is not returnned)",
					HttpStatus.NOT_ACCEPTABLE);

		} else {
			bookService.delete(book);
			return new ResponseEntity<String>("Book Deleted Succesfully", HttpStatus.OK);
		}

	}

}
