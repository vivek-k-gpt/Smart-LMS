package com.gl.smartlms.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.Category;
import com.gl.smartlms.model.User;
import com.gl.smartlms.repository.CategoryRepository;
import com.gl.smartlms.service.CategoryService;
import com.gl.smartlms.service.CategoryServiceImpl;



@SpringBootTest(classes = { CategoryServiceTest.class })
public class CategoryServiceTest {
	
	@Mock
	CategoryRepository categoryRepository;
	
	@InjectMocks
	CategoryServiceImpl categoryServiceImpl;
	
	
	@Test
	@Order(1)
	void saveTest() {
		Category cat = getCategory();
		
		when(categoryRepository.save(cat)).thenReturn(cat);
		
		assertSame(cat, categoryServiceImpl.save(cat));
	
	}
	@Test
	@Order(2)
	void addNewTest() {
		Category cat = getCategory();
		
		when(categoryRepository.save(cat)).thenReturn(cat);
		assertSame(cat, categoryServiceImpl.save(cat));
		
	}
	
	@Test
	@Order(3)
	void getCategoryTest() {

		List<Category> catList = new ArrayList<>();

		Category cat1 = getCategory();
		catList.add(cat1);

		Category category = new Category();
		category.setId(2l);
		category.setName("drama");
		category.setShortName("dar");
		category.setNotes("this contains Drama category book");
		

		catList.add(category);

		when(categoryRepository.findById(1l)).thenReturn(findCategory(1l, catList));
		Category category1 = categoryServiceImpl.getCategory(1l).get();
		assertSame("comic", category1.getName());
	}
	

	@Test
	@Order(4)
	void getAllTest() {
		List<Category> catList = new ArrayList<>();

		Category cat1 = getCategory();
		catList.add(cat1);
		when(categoryRepository.findAll()).thenReturn(catList);
		assertEquals(1, categoryServiceImpl.getAll().size());
	}
	
	
	
	
	@Test
	@Order(5)
	void getTotalCountTest() {
		List<Category> catList = new ArrayList<>();

		Category cat1 = getCategory();
		catList.add(cat1);

		when(categoryRepository.count()).thenReturn((long) catList.size());
		Long catsize = categoryServiceImpl.getTotalCount();
		assertEquals(catList.size(), catsize);
	}
	
	
	@Test
	@Order(6)
	void deleteCategoryTest() {
		Category cat = getCategory();
		categoryServiceImpl.deleteCategory(cat.getId());
		verify(categoryRepository,times(1)).deleteById(1l);
	}


	
	
	public Optional<Category> findCategory(Long id, List<Category> cat) {
		Optional<Category> catfinal = null;
		for (Category category : cat) {
			if (category.getId() == id) {
				catfinal = Optional.ofNullable(category);
			}
		}
		return catfinal;

	}
	
	
	private Category getCategory() {
		Category category = new Category();
		category.setId(1l);
		category.setName("comic");
		category.setShortName("com");
		category.setNotes("this contains comic category book");
		
		return category;
	}

}
