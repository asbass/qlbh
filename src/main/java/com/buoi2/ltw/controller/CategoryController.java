package com.buoi2.ltw.controller;

import com.buoi2.ltw.dto.CategoryDTO;
import com.buoi2.ltw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// Lấy tất cả danh mục (không phân trang)
	@GetMapping("/index")
	public ResponseEntity<List<CategoryDTO>> index() {
		List<CategoryDTO> categories = categoryService.findAll();
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<CategoryDTO> edit(@PathVariable("id") String id) {
		CategoryDTO category = categoryService.getCategoryById(id);
		if (category != null) {
			return ResponseEntity.ok(category);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/add")
	public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
		return ResponseEntity.ok(createdCategory);
	}

	@PutMapping("/update")
	public ResponseEntity<CategoryDTO> update(@RequestBody CategoryDTO categoryDTO) {
		CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO);
		if (updatedCategory != null) {
			return ResponseEntity.ok(updatedCategory);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		boolean isDeleted = categoryService.deleteCategory(id);
		if (isDeleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Tìm kiếm không phân trang
	@GetMapping("/search")
	public ResponseEntity<List<CategoryDTO>> search(@RequestParam("keywords") Optional<String> kw) {
		String keywords = kw.orElse("");  // Nếu không có từ khóa, tìm tất cả
		List<CategoryDTO> categories = categoryService.searchCategories(keywords);
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/reset")
	public ResponseEntity<String> reset() {
		return ResponseEntity.ok("Session reset");
	}
}
