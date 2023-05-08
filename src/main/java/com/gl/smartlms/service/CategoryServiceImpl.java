package com.gl.smartlms.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.smartlms.advice.CategoryNotFoundException;
import com.gl.smartlms.advice.NoContentFoundException;
import com.gl.smartlms.model.Category;
import com.gl.smartlms.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category addNew(Category category) {
		category.setCreateDate(new Date());
		return categoryRepository.save(category);
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Optional<Category> getCategory(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isEmpty()) {
			throw new CategoryNotFoundException("Category Is Not Available with id "+id);
		}
		return category;
	}

	@Override
	public List<Category> getAll() {
		List<Category> category = categoryRepository.findAll();
		if(category.isEmpty()) {
			throw new NoContentFoundException("No Category is Present List is Empty");
		}
		return category;

	}

	@Override
	public Long getTotalCount() {
		return categoryRepository.count();
	}

	@Override
	public List<Category> getAllBySort() {
		List<Category> category = categoryRepository.findAllByOrderByNameAsc();
		if(category.isEmpty()) {
			throw new NoContentFoundException("No Category is Present List is Empty");
		}
		return category;
	}

	@Override
	public Optional<Category> getCategoryByName(String name) {
	
		Optional<Category> category = categoryRepository.findByName(name);
		if(category.isEmpty()) {
			throw new CategoryNotFoundException("No Category is Present With Name "+ name);
		}
		
		return category;
	}
	
	

	@Override
	public boolean hasUsage(Category category) {
		return category.getBooks().size() > 0;
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
		
	}

	@Override
	public void deleteCategoryByCategoryObject(Category category) {
		categoryRepository.delete(category);
		
	}

}