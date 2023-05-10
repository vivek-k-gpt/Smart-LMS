package com.gl.smartlms.restController;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;


import com.gl.smartlms.model.Category;

import com.gl.smartlms.service.CategoryService;

import java.util.List;

import javax.validation.Valid;

@RestController

public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;

	// ==============================================================
	// Add Category Api
	// ==============================================================
	@RequestMapping(value = "api-librarian/category/add", method = RequestMethod.POST)
	public ResponseEntity<String> saveCategory(@Valid @RequestBody Category category) {
		category = categoryService.addNew(category);
		return new ResponseEntity<String>("Category Added with type  " + category.getName(), HttpStatus.CREATED);
	}

	// ==============================================================
	// Update/Edit Category Details Api
	// ==============================================================
	@PutMapping("api-librarian/category/update")
	public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category) {
		Category cat = categoryService.getCategory(category.getId()).get();
		category.setCreateDate(cat.getCreateDate());
		categoryService.save(category);
		return new ResponseEntity<String>("Category Updated With Type  " + cat.getName(), HttpStatus.ACCEPTED);
	}

	// ==============================================================
	// List Category Api
	// ==============================================================
	@GetMapping("api-all/category/list")
	public ResponseEntity<List<Category>> showAllMembers() {
		List<Category> clist = categoryService.getAll();
		return new ResponseEntity<List<Category>>(clist, HttpStatus.FOUND);
	}

	// ==============================================================
	// List(Sorted) Category Api
	// ==============================================================
	@GetMapping("api-all/category/sorted-list")
	public ResponseEntity<List<Category>> showAllCategorySortedByName() {
		List<Category> clist = categoryService.getAllBySort();
		return new ResponseEntity<List<Category>>(clist, HttpStatus.FOUND);
	}

	// ==============================================================
	// Count total Category Api
	// ==============================================================
	@GetMapping("api-admin-librarian/category/count")
	public ResponseEntity<String> countAllCategory() {
		Long categoryCount = categoryService.getTotalCount();

		return new ResponseEntity<String>(categoryCount.toString(), HttpStatus.OK);
	}

	// ==============================================================
	// Find Category By Id Api
	// ==============================================================
	@GetMapping(value = "api-all/category/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
		Category category = categoryService.getCategory(id).get();
		return new ResponseEntity<Category>(category, HttpStatus.FOUND);
	}

	// ==============================================================
	// Delete Category By Id Api
	// ==============================================================
	@DeleteMapping("api-librarian/category/delete/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {
		Category category = categoryService.getCategory(id).get();
		if (categoryService.hasUsage(category)) {
			return new ResponseEntity<String>(
					"category is in Use...can not be deleted (Books Are Added in this category)", HttpStatus.OK);
		} else {
			categoryService.deleteCategory(id);
			return new ResponseEntity<String>("Category deleted Suceesfully", HttpStatus.ACCEPTED);
		}
	}

}
