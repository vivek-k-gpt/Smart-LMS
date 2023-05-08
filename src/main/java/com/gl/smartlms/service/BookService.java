package com.gl.smartlms.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Category;
 

@Service
public interface BookService {
	
	
	public List<Book> getByTag(String tag);

	public Book addNewBook(Book book);

	public Book saveBook(Book book);

	public Optional<Book> getBookById(Long id);

	public List<Book> getAll();
	
	public Book getBook(Long id);

	public Long getTotalCount();

	public List<Book> getByAuthorName(String authors);

	public List<Book> getBooksByIdList(List<Long> ids);

	public List<Book> getByCategory(Category category);

	public List<Book> getBookWithTitle(String title);

	public List<Book> getAvaialbleBooks();

	public List<Book> geAvailabletByCategory(Category category);

	public List<Book> getBypublisherName(String publisher);

	public List<Book> checkAvailableBooks();

	public List<Book> checkIssuedBooks();

	public List<Book> listCategoryIssuedBooks(Category category);

	public List<Book> listCategoryAvailableBooks(Category category);

	public Long getAvailableBookCount();

	public Long getIssuedBookCount();

	public void delete(Book book);


	



}
