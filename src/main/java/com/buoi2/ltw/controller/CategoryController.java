package com.buoi2.ltw.controller;

import java.util.Optional;

import com.buoi2.ltw.dao.CategoryDAO;
import com.buoi2.ltw.entity.Category;
import com.buoi2.ltw.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("admin/categories")
public class CategoryController {

	@Autowired
	CategoryDAO dao;

	@Autowired
	SessionService session;

	@GetMapping("/index")
	public ResponseEntity<Page<Category>> index(@RequestParam("c") Optional<Integer> c) {
		Pageable pageable = PageRequest.of(c.orElse(0), 5);
		Page<Category> page = dao.findAll(pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/edit/{id}")
	public ResponseEntity<Category> edit(@PathVariable("id") String id) {
		Optional<Category> item = dao.findById(id);
		if (item.isPresent()) {
			return ResponseEntity.ok(item.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/add")
	public ResponseEntity<Category> create(@RequestBody Category item) {
		Category savedItem = dao.save(item);
		return ResponseEntity.ok(savedItem);
	}

	@PutMapping("/update")
	public ResponseEntity<Category> update(@RequestBody Category item) {
		if (dao.existsById(item.getId())) {
			Category updatedItem = dao.save(item);
			return ResponseEntity.ok(updatedItem);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		if (dao.existsById(id)) {
			dao.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	//chưa sửa xong
	@GetMapping("/search")
	public ResponseEntity<Page<Category>> search(@RequestParam("keywords") Optional<String> kw,
												 @RequestParam("c") Optional<Integer> c) {
		String keywords = kw.orElse(session.get("keywords", ""));
		session.set("keywords", keywords);
		Pageable pageable = PageRequest.of(c.orElse(0), 5);
		Page<Category> page = dao.findAllByNameLike("%" + keywords + "%", pageable);
		return ResponseEntity.ok(page);
	}

	@GetMapping("/reset")
	public ResponseEntity<String> reset() {
		return ResponseEntity.ok("Session reset");
	}
}
