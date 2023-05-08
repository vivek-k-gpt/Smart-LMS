package com.gl.smartlms.repository;



import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.gl.smartlms.model.Book;
import com.gl.smartlms.model.Category;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	public List<Book> findByTag(String tag);

	public List<Book> findByAuthors(String authors);

	public List<Book> findByCategory(Category category);

	public List<Book> findByTitle(String title);

	
	@Query(value="Select * from Book where status = 1" ,nativeQuery=true)
	public List<Book> findAvailableBooks();

	public List<Book> findByCategoryAndStatus(Category category, Integer bookStatusAvailable);

	public List<Book> findByPublisher(String publisher);
	
	@Query("FROM Book WHERE status = :status")
	public List<Book> findAllAvailableBooks(Integer status);
	
	@Query("FROM Book WHERE status = :status")
	public List<Book> findAllIssuedBooks(Integer status);

	
	@Query(value = "SELECT count(id) FROM Book where status = :status")
	public Long countBooksBasedOnStatus(@Param ("status") Integer status);
	
	


	
}
