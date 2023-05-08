package com.gl.smartlms.restController;

import org.springframework.http.HttpStatus;
import java.util.List;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Category;

import com.gl.smartlms.service.BookService;
import com.gl.smartlms.service.CategoryService;

@RestController
@RequestMapping("/api-book")
public class BookRestController {

	@Autowired
	private BookService bookService;

	@Autowired
	private CategoryService categoryService;

	ObjectMapper Obj = new ObjectMapper();

// ==============================================================//
	// Create//
// ==============================================================//

	// ==============================================================
	// Add Category Api (Admin)
	// ==============================================================
	@PostMapping("/add/{id}")
	public ResponseEntity<String> addBook(@RequestBody Book book, @PathVariable("id") Long id) {
		Optional<Category> optional = categoryService.getCategory(id);
		try {
			if (optional.isPresent()) {
				book.setCategory(optional.get());

				if (bookService.getByTag(book.getTag()) != null) {

					return new ResponseEntity<String>("tag already exist", HttpStatus.NOT_ACCEPTABLE);
				} else {
					bookService.addNewBook(book);

					return new ResponseEntity<String>("Book get Added with Title " + book.getTitle() + " and Category "
							+ optional.get().getName(), HttpStatus.CREATED);
				}

			} else {

				return new ResponseEntity<String>("Category Not Available ....(First create the category)",
						HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

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
		if (bookCount != 0) {
			return new ResponseEntity<String>(bookCount.toString(), HttpStatus.OK);
		}
		return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// Count Available Book Api (Admin)
	// ==============================================================
	@GetMapping(value = "/available/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> countAllAvaialbleBooks() {
		Long bookCount = bookService.getAvailableBookCount();
		if (bookCount != 0) {
			return new ResponseEntity<String>(bookCount.toString(), HttpStatus.OK);
		}
		return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// Count Issued Book Api (Admin)
	// ==============================================================
	@GetMapping(value = "/issued/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> countAllIssuedBooks() {
		Long bookCount = bookService.getIssuedBookCount();
		if (bookCount != 0) {
			return new ResponseEntity<String>(bookCount.toString(), HttpStatus.OK);
		}
		return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
	}

// ==============================================================
	// LIST (Book Based Filtering)
// ==============================================================

	// ==============================================================
	// List All Book Api (Admin + User)
	// ==============================================================
	@GetMapping("/list")
	public ResponseEntity<List<Book>> showAllBooks() {

		List<Book> list = bookService.getAll();
		try {
			if (list != null) {
				return new ResponseEntity<List<Book>>(list, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);

	}

	// ==============================================================
	// List All Book(By Title) Api (Admin + User)
	// ==============================================================
	@GetMapping("/list-by-title/{title}")
	public ResponseEntity<List<Book>> getBytitle(@PathVariable String title) {

		List<Book> list = bookService.getBookWithTitle(title);
		try {
			if (list != null) {
				return new ResponseEntity<List<Book>>(list, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Book>>(HttpStatus.OK);
	}

	// ==============================================================
	// List All Book(By Tagname) Api (Admin + User)
	// ==============================================================
	@GetMapping("/list-by-tag/{tag}")
	public ResponseEntity<List<Book>> findBookBytag(@PathVariable String tag) {

		List<Book> book = bookService.getByTag(tag);
		try {
			if (book != null) {

				return new ResponseEntity<List<Book>>(book, HttpStatus.FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	// ==============================================================
	// List All Book(By Authors) Api (Admin + User)
	// ==============================================================
	@GetMapping(value = "/list-by-author/{authors}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBooksByAuthor(@PathVariable("authors") String authors) {

		List<Book> book = bookService.getByAuthorName(authors);
		try {
			if (book != null) {

				String bookJson = Obj.writeValueAsString(book);

				return new ResponseEntity<String>(bookJson, HttpStatus.FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// List All Book(By publisher) Api (Admin + User)
	// ==============================================================
	@GetMapping(value = "/list-by-publisher/{publisher}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getBooksByPublisher(@PathVariable String publisher) {

		List<Book> book = bookService.getBypublisherName(publisher);
		try {
			if (book != null) {

				String bookJson = Obj.writeValueAsString(book);

				return new ResponseEntity<String>(bookJson, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

// ==============================================================
	// List All Available Books Api (Admin + User)
// ==============================================================
	@GetMapping(value = "/available-books", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllAvailableBooks() {
		List<Book> availableBooks = bookService.checkAvailableBooks();
		try {
			if (availableBooks != null) {
				String bookJson = Obj.writeValueAsString(availableBooks);
				return new ResponseEntity<String>(bookJson, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

// ==============================================================
	// List All Issued Books Api (Admin )
// ==============================================================
	@GetMapping("/issued-books")
	public ResponseEntity<String> getAllIssuedBooks() {
		List<Book> availableBooks = bookService.checkIssuedBooks();
		try {
			if (availableBooks != null) {
				String bookJson = Obj.writeValueAsString(availableBooks);
				return new ResponseEntity<String>(bookJson, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No Book Is Available", HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//==============================================================
//				LIST CAtegory Based Filtering
//==============================================================

//==============================================================
// List All Books In A category (By Category Name)
//==============================================================	
	@GetMapping(value = "/category/all-books/{category_name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAvailableBooksInCategory(@PathVariable("category_name") String name) {

		Optional<Category> category = categoryService.getCategory(name);
		try {
			if (category.isPresent()) {
				List<Book> list = bookService.geAvailabletByCategory(category.get());
				if (list != null) {
					String bookJson = Obj.writeValueAsString(list);
					return new ResponseEntity<String>(bookJson, HttpStatus.FOUND);
				} else {
					return new ResponseEntity<String>(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
				}

			} else {
				return new ResponseEntity<String>("Category Does not Exist", HttpStatus.OK);
			}
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//==============================================================
//List All Books In A category (By Category Id)
//==============================================================		
	@GetMapping(value = "/category/all/{category_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findBooksByCategory(@PathVariable("category_id") Long id) {
		String bookJson;
		Optional<Category> category = categoryService.getCategory(id);
		try {
			if (category.isPresent()) {
				List<Book> list = bookService.getByCategory(category.get());

				bookJson = Obj.writeValueAsString(list);
				return new ResponseEntity<String>(bookJson, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>("Categorgy not Exist", HttpStatus.OK);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

//==============================================================
//List All Issued Books In A category (By Category Name)
//==============================================================
	@GetMapping(value = "/category/issued/all/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllIssuedBooksInCategory(@PathVariable String type) {
		Optional<Category> category = categoryService.getCategory(type);
		try {
			if (category.isPresent()) {
				List<Book> issuedBooks = bookService.listCategoryIssuedBooks(category.get());
				String bookJson = Obj.writeValueAsString(issuedBooks);
				return new ResponseEntity<String>(bookJson, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No Book is Issued in this category", HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//==============================================================
//List All Available Books(Not Issued) In A category (By Category Name)
//==============================================================
	@GetMapping(value = "/category/available/all/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllAvailableBooksInCategory(@PathVariable String type) {
		Optional<Category> category = categoryService.getCategory(type);
		try {
			if (category.isPresent()) {
				List<Book> availableBooks = bookService.listCategoryAvailableBooks(category.get());
				String bookJson = Obj.writeValueAsString(availableBooks);
				return new ResponseEntity<String>(bookJson, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No Book is Available in this category", HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//==============================================================
//						FIND
//==============================================================

// ==============================================================
// find All Book(By id) Api (Admin + User)
// ==============================================================
	@GetMapping(value = "find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findBookById(@PathVariable Long id) {

		Optional<Book> optional = bookService.getBookById(id);

		try {
			if (optional.isPresent()) {
				Book book = optional.get();
				String bookJson = Obj.writeValueAsString(book);
				return new ResponseEntity<>(bookJson, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//==============================================================
//find All Books  By List of Book ids (Admin + User)
//==============================================================
	@GetMapping(value = "/find-books/{ids}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findBooksWithIdList(@PathVariable List<Long> ids) {
		List<Book> list = bookService.getBooksByIdList(ids);
		try {
			String bookJson = Obj.writeValueAsString(list);
			return new ResponseEntity<String>(bookJson, HttpStatus.FOUND);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	
	
	
//==============================================================
//			Update
//==============================================================
		
	
	
	
	
//==============================================================
//	Update Book Details (SAMECategory) (Admin)
//==============================================================	
	@PutMapping("/update")
	public ResponseEntity<String> updateBook(@RequestBody Book book) {
		Optional<Book> optional = bookService.getBookById(book.getId());
		if (optional.isPresent()) {
			Optional<Category> category = categoryService.getCategory(optional.get().getCategory().getId());

			book.setCategory(category.get());
			book.setCreateDate(optional.get().getCreateDate());
			book.setStatus(optional.get().getStatus());
			bookService.saveBook(book);
			return new ResponseEntity<String>("Succesfully Updated Book Details", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<String>("Details Not updated", HttpStatus.NOT_ACCEPTABLE);

	}

//==============================================================
//Update Book Details (SAMECategory) (Admin)
//==============================================================
	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateBook(@RequestBody Book book, @PathVariable Long id) {

		Optional<Book> optional = bookService.getBookById(book.getId());
		if (optional.isPresent()) {
			Optional<Category> category = categoryService.getCategory(id);

			Category oldcategory = optional.get().getCategory();

			oldcategory.getBooks().remove(optional.get());

			book.setCategory(category.get());
			book.setCreateDate(optional.get().getCreateDate());
			book.setStatus(optional.get().getStatus());

			bookService.saveBook(book);

			try {
				String bookJson = Obj.writeValueAsString(book);
				return new ResponseEntity<String>(
						"Succesfully Updated Book Details category of type" + category.get().getName() + " " + bookJson,
						HttpStatus.ACCEPTED);
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}

		}
		return new ResponseEntity<String>("Details Not updated", HttpStatus.NOT_ACCEPTABLE);

	}

	
	
	
	
	
//==============================================================
//	Delete
//==============================================================
	
	
//==============================================================
//Delete Book By Id (Admin)
//==============================================================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id) {
		Book book = bookService.getBook(id);
		try {
			if (book != null) {
				if (book.getStatus() == Constants.BOOK_STATUS_ISSUED) {

					return new ResponseEntity<String>("Book in use Can net be delete (book is not returnned)",
							HttpStatus.NOT_ACCEPTABLE);

				} else {
					bookService.delete(book);
					return new ResponseEntity<String>("Book Deleted Succesfully", HttpStatus.OK);
				}

			} else {
				return new ResponseEntity<String>("Book Not found", HttpStatus.NOT_FOUND);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
