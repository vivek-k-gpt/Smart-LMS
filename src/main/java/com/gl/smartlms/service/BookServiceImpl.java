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

import com.gl.smartlms.constants.*;


@Service
public class BookServiceImpl  implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public List<Book> getByTag(String tag) {
		return bookRepository.findByTag(tag);
	}

	@Override
	public Book addNewBook(@Valid Book book) {
		book.setCreateDate(new Date());
		book.setStatus( Constants.BOOK_STATUS_AVAILABLE );
		return bookRepository.save(book);
		
	}

	@Override
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public Optional<Book> getBookById(Long id) {
		return bookRepository.findById(id);
	}

	@Override
	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book getBook(Long id) {
		return bookRepository.findById(id).get();
	}
	
	@Override
	public Long getTotalCount() {
		return bookRepository.count();
	}

	@Override
	public List<Book> getByAuthorName(String authors) {
	
		return bookRepository.findByAuthors(authors);
	}

	@Override
	public List<Book> getBooksByIdList(List<Long> ids) {
		return bookRepository.findAllById(ids);
	}
	@Override
	public List<Book> getByCategory(Category category) {
		
		return bookRepository.findByCategory(category);
	}

	@Override
	public List<Book> getBookWithTitle(String title) {
		
		return bookRepository.findByTitle(title);
	}

	@Override
	public List<Book> getAvaialbleBooks() {
	
		return bookRepository.findAvailableBooks();
	}

	@Override
	public List<Book> geAvailabletByCategory(Category category) {
		return bookRepository.findByCategoryAndStatus(category, Constants.BOOK_STATUS_AVAILABLE);
	}

	@Override
	public List<Book> getBypublisherName(String publisher) {
		return bookRepository.findByPublisher(publisher);
	}

	@Override
	public List<Book> checkAvailableBooks() {
		
		return bookRepository.findAllAvailableBooks(Constants.BOOK_STATUS_AVAILABLE);
	}

	@Override
	public List<Book> checkIssuedBooks() {
			return bookRepository.findAllIssuedBooks(Constants.BOOK_STATUS_ISSUED);
	}



	@Override
	public List<Book> listCategoryIssuedBooks(Category category) {
		return bookRepository.findByCategoryAndStatus(category, Constants.BOOK_STATUS_ISSUED);
	}

	@Override
	public List<Book> listCategoryAvailableBooks(Category category) {
		
		return  bookRepository.findByCategoryAndStatus(category, Constants.BOOK_STATUS_AVAILABLE);
	}

	@Override
	public Long getAvailableBookCount() {
		
		return  bookRepository.countBooksBasedOnStatus(Constants.BOOK_STATUS_AVAILABLE);
	}

	@Override
	public Long getIssuedBookCount() {
	
		return bookRepository.countBooksBasedOnStatus(Constants.BOOK_STATUS_ISSUED);


	}

	@Override
	public void delete(Book book) {
	
		 bookRepository.delete(book);
	}
	

}
