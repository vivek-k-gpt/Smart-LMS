package com.gl.smartlms.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gl.smartlms.model.Category;



@Service
public interface CategoryService {

	
	public Category addNew(Category category);

	public Category save(Category category);

	public Optional<Category> getCategory(Long id);
	
	public List<Category> getAll();
	
	public Long getTotalCount();
	
	public List<Category> getAllBySort();

	public Optional<Category> getCategory(String name);
	
	public boolean hasUsage(Category category);

	public void deleteCategory(Long id);

	public void deleteCategoryByCategoryObject(Category category);
}
