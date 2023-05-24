package com.gl.smartlms.serviceTest;

import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;


import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Category;
import com.gl.smartlms.repository.BookRepository;
import com.gl.smartlms.service.BookServiceImpl;

@SpringBootTest(classes = { BookServiceTest.class })
public class BookServiceTest {

	
	 @InjectMocks
	    private BookServiceImpl bookServiceImpl;
	    
	    @Mock
	    private BookRepository bookRepository;
	    
	    @Autowired
	    static Book book;
	    
	    static List<Book> books=new ArrayList<Book>();
	    
	    public static Category buildCategory() {
	        Category category = new Category();
	        category.setId((long) 2);
	        category.setName("comic");
	        category.setNotes("contains comic category");
	        category.setShortName("com");
	        return category;
	        }
	    
	    @BeforeAll
	    public static void init() {
	        Category category=buildCategory();
	         book=new Book();
	        book.setId((long) 1);
	        book.setTitle("Two states");
	        book.setAuthors("chetan bhagat");
	        book.setIsbn("1223");
	        book.setPublisher("dharma");
	        book.setStatus(0);
	        book.setCategory(category);
	        books.add(book);
	        
	    }
	     
	            
	        
	    
	    @Test
	    public void addNewBookTest() {
	        book.setCreateDate(new Date());
	        book.setStatus(Constants.BOOK_STATUS_AVAILABLE);
	        when(bookRepository.save(book)).thenReturn(book);
	        assertEquals(book,bookServiceImpl.addNewBook(book));
	    }
	    
	    @Test
	    public void saveBookTest() {
	        
	        when(bookRepository.save(book)).thenReturn(book);
	        assertEquals(book,bookServiceImpl.addNewBook(book));
	        
	    }
	    
	    @Test
	    public void getBookByIdTest() {
	        long id=book.getId();
	        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
	        assertEquals(Optional.of(book), bookServiceImpl.getBookById(id));
	    }
	    
	    @Test
	    public void getAllTest() {
	        when(bookRepository.findAll()).thenReturn(books);
	        assertEquals(books, bookServiceImpl.getAll());
	    }
	    
	    @Test
	    public void getTotalCountTest() {
	        when(bookRepository.count()).thenReturn((long) books.size());
	        assertEquals(books.size(),bookServiceImpl.getTotalCount());
	    }
	    
	    @Test
	    public void getByCategoryTest() {
	        Category category=buildCategory();
	        when(bookRepository.findByCategory(category)).thenReturn(books);
	        assertEquals(books,bookServiceImpl.getByCategory(category));
	        
	    }
	    
	    @Test
	    public void listCategoryAvailableBooksTest() {
	        Category category =buildCategory();
	        when(bookRepository.findByCategoryAndStatus(category, Constants.BOOK_STATUS_AVAILABLE)).thenReturn(books);
	        assertEquals(books, bookServiceImpl.listCategoryAvailableBooks(category));
	    }

	    @Test
	    public void getAvailableBookCount() {

	        when( bookRepository.countBooksBasedOnStatus(Constants.BOOK_STATUS_AVAILABLE)).thenReturn((long) books.size());
	        assertEquals(books.size(), bookServiceImpl.getAvailableBookCount());
	    }
	    
	
}
