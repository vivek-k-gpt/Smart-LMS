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


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gl.smartlms.constants.Constants;
import com.gl.smartlms.model.Category;

import com.gl.smartlms.service.CategoryService;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api-category")
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;

	ObjectMapper Obj = new ObjectMapper();

	// ==============================================================
	// Add Category Api (Admin)
	// ==============================================================
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<String> saveCategory(@Valid @RequestBody Category category) {

		category = categoryService.addNew(category);
		try {
			if (category != null) {
				return new ResponseEntity<String>("Category Added with type  " + category.getName(),
						HttpStatus.CREATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// Update/Edit Category Details Api (Admin)
	// ==============================================================
	@PutMapping("/update")
	public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category) {

		Optional<Category> optional = categoryService.getCategory(category.getId());
		try {
			if (optional.isPresent()) {
				category.setCreateDate(optional.get().getCreateDate());
				categoryService.save(category);

				return new ResponseEntity<String>("Category Updated With Type  " + optional.get().getName(),
						HttpStatus.ACCEPTED);
			} else {

				return new ResponseEntity<String>("Category Not Found", HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// List Category Api (Admin + user)
	// ==============================================================
	@GetMapping("/list")
	public ResponseEntity<List<Category>> showAllMembers() {

		List<Category> clist = categoryService.getAll();
		try {
			if (clist != null) {

				return new ResponseEntity<List<Category>>(clist, HttpStatus.FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	// ==============================================================
	// List(Sorted) Category Api (Admin + User)
	// ==============================================================
	@GetMapping("/sorted/list")
	public ResponseEntity<List<Category>> showAllMembersSortedByName() {

		List<Category> clist = categoryService.getAllBySort();
		try {
			if (clist != null) {
				return new ResponseEntity<List<Category>>(clist, HttpStatus.FOUND);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// Count total Category Api (Admin)
	// ==============================================================
	@GetMapping("/total/count")
	public ResponseEntity<String> countAllCategory() {
		Long categoryCount = categoryService.getTotalCount();
		try {
			if (categoryCount != 0) {
				return new ResponseEntity<String>(categoryCount.toString(), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
	}

	// ==============================================================
	// Find Category By Id Api (Admin)
	// ==============================================================
	@GetMapping(value = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findCategoryById(@PathVariable Long id) {
		Optional<Category> optional = categoryService.getCategory(id);

		try {
			if (optional.isPresent()) {
				Category category = optional.get();

				String categoryJson = Obj.writeValueAsString(category);
				return new ResponseEntity<String>(categoryJson, HttpStatus.FOUND);

			} else {
				return new ResponseEntity<String>(Constants.NO_CONTENT, HttpStatus.NO_CONTENT);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// Delete Category By Id Api (Admin)
	// ==============================================================
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable Long id) {

		try {
			Optional<Category> optional = categoryService.getCategory(id);
			if (optional.isPresent()) {
				if (categoryService.hasUsage(optional.get())) {
					return new ResponseEntity<String>(
							"category is in Use...can not be deleted (Books Are Added in this category)",
							HttpStatus.OK);
				} else {
					categoryService.deleteCategory(id);
					return new ResponseEntity<String>("Category deleted Suceesfully", HttpStatus.ACCEPTED);
				}

			} else {
				return new ResponseEntity<String>("Category does not exist", HttpStatus.OK);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ==============================================================
	// Delete Category (Admin)
	// ==============================================================
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCategory(@RequestBody Category category) {
		try {
			Optional<Category> optional = categoryService.getCategory(category.getId());
			if (optional.isPresent()) {
				if (categoryService.hasUsage(optional.get())) {
					return new ResponseEntity<String>(
							"category is in Use...can not be deleted (Books Are Added in this category", HttpStatus.OK);
				} else {
					categoryService.deleteCategoryByCategoryObject(optional.get());
					return new ResponseEntity<String>("Category deleted Suceesfully", HttpStatus.ACCEPTED);
				}
			} else {
				return new ResponseEntity<String>("Category does not exist", HttpStatus.OK);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return Constants.getResponseEntity(Constants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
