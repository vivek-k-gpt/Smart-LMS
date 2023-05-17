package com.gl.smartlms.service;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Category;
import com.gl.smartlms.repository.BookRepository;
import com.gl.smartlms.advice.BookNotFoundException;
import com.gl.smartlms.advice.NoContentFoundException;
import com.gl.smartlms.constants.*;

//==============================================================
//= BookService  implementation 
//=================================================================
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Optional<Book> getByTagInCategory(String tag, Category category) {
		return bookRepository.findByCategoryAndTag(category, tag);
	}

	@Override
	public Book addNewBook(@Valid Book book) {
		book.setCreateDate(new Date());
		book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
		return bookRepository.save(book);

	}

	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Optional<Book> getBookById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		if (book.isEmpty()) {
			throw new BookNotFoundException("No book is found with id :" + id);
		}
		return book;
	}

	@Override
	public List<Book> getAll() {

		List<Book> list = bookRepository.findAll();
		if (list.isEmpty()) {
			throw new NoContentFoundException("No Book is Present List is Empty");
		}

		return list;
	}

	@Override
	public Long getTotalCount() {
		return bookRepository.count();
	}

	@Override
	public List<Book> getByAuthorName(String authors) {

		List<Book> book = bookRepository.findByAuthors(authors);
		if (book.isEmpty()) {
			throw new BookNotFoundException("No Book is Found For Author :" + authors);
		}
		return book;
	}

	@Override
	public List<Book> getBooksByIdList(List<Long> ids) {
		List<Book> book = bookRepository.findAllById(ids);
		if (book.isEmpty()) {
			throw new NoContentFoundException("The list is Empty ...No book ..plz make Valid Selection");
		}
		return book;
	}

	@Override
	public List<Book> getByCategory(Category category) {

		List<Book> book = bookRepository.findByCategory(category);
		if (book.isEmpty()) {
			throw new NoContentFoundException("The list is Empty ...No book is this Category");
		}
		return book;
	}

	@Override
	public List<Book> getBookWithTitle(String title) {

		List<Book> list = bookRepository.findByTitle(title);
		if (list.isEmpty()) {
			throw new NoContentFoundException("No Book is Present List is Empty");
		}

		return list;
	}

	@Override
	public List<Book> getAvaialbleBooks() {

		return bookRepository.findAvailableBooks();
	}

	@Override
	public List<Book> geAvailabletByCategory(Category category) {
		List<Book> book = bookRepository.findByCategory(category);

		if (book.isEmpty()) {
			throw new NoContentFoundException("The list is Empty ...No book is this Category");
		}
		return book;
	}

	@Override
	public List<Book> getBypublisherName(String publisher) {
		List<Book> book = bookRepository.findByPublisher(publisher);
		if (book.isEmpty()) {
			throw new NoContentFoundException("No Book is present for the Publisher" + publisher);
		}
		return book;
	}

	@Override
	public List<Book> checkAvailableBooks() {

		List<Book> book = bookRepository.findAllAvailableBooks(Constants.BOOK_STATUS_AVAILABLE);
		if (book.isEmpty()) {
			throw new NoContentFoundException("No Book Is Available");
		}
		return book;
	}

	@Override
	public List<Book> checkIssuedBooks() {

		List<Book> book = bookRepository.findAllIssuedBooks(Constants.BOOK_STATUS_ISSUED);

		if (book.isEmpty()) {
			throw new NoContentFoundException("No Book is Issued");

		}
		return book;
	}

	@Override
	public List<Book> listCategoryIssuedBooks(Category category) {
		List<Book> book = bookRepository.findByCategoryAndStatus(category, Constants.BOOK_STATUS_ISSUED);
		if (book.isEmpty()) {
			throw new NoContentFoundException("No book is Issued in the Category " + category.getName());
		}
		return book;
	}

	@Override
	public List<Book> listCategoryAvailableBooks(Category category) {

		List<Book> book = bookRepository.findByCategoryAndStatus(category, Constants.BOOK_STATUS_AVAILABLE);
		if (book.isEmpty()) {
			throw new NoContentFoundException("No book is Available in the Category " + category.getName());
		}
		return book;
	}

	@Override
	public Long getAvailableBookCount() {

		return bookRepository.countBooksBasedOnStatus(Constants.BOOK_STATUS_AVAILABLE);
	}

	@Override
	public Long getIssuedBookCount() {

		return bookRepository.countBooksBasedOnStatus(Constants.BOOK_STATUS_ISSUED);

	}

	@Override
	public void delete(Book book) {

		bookRepository.delete(book);
	}

	@Override
	public List<Book> getBookWithTag(String tag) {
		List<Book> book = bookRepository.findByTag(tag);

		if (book.isEmpty()) {
			throw new NoContentFoundException("No Books  are available with tag" + tag + "List is Empty");
		}
		return book;
	}

}
