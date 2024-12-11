package com.buoi2.ltw.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;


import com.buoi2.ltw.dao.CategoryDAO;
import com.buoi2.ltw.dao.ProductDAO;
import com.buoi2.ltw.entity.Category;
import com.buoi2.ltw.entity.Product;
import com.buoi2.ltw.service.SessionService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping("admin/product")
public class ProductController {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private CategoryDAO categoryDao;

	@Autowired
	private SessionService session;

	@Autowired
	private ServletContext app;

	// Endpoint to get the product creation page
	@GetMapping("/index")
	public ResponseEntity<?> getCreateProductPage() {
		List<Product> products = productDao.findAll();
		return ResponseEntity.ok(products);
	}

	// Endpoint to edit a product
	@GetMapping("/edit/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") int id) {
		Optional<Product> productOpt = productDao.findById(id);
		if (productOpt.isPresent()) {
			return ResponseEntity.ok(productOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
	}

	// Endpoint to add a new product
	@PostMapping("/add")
	public ResponseEntity<?> addProduct(
			@ModelAttribute Product product,
			@RequestParam("images") MultipartFile image) {

		if (image.isEmpty()) {
			return ResponseEntity.badRequest().body("Please upload an image file");
		}

		try {
			String filename = image.getOriginalFilename();
			File file = new File(app.getRealPath("/images/" + filename));
			image.transferTo(file);

			product.setImage(filename);
			productDao.save(product);

			return ResponseEntity.ok("Product added successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving product");
		}
	}

	// Endpoint to update a product
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(
			@ModelAttribute Product product,
			@RequestParam("images") MultipartFile image) {

		if (image.isEmpty()) {
			return ResponseEntity.badRequest().body("Please upload an image file");
		}

		try {
			String filename = image.getOriginalFilename();
			File file = new File(app.getRealPath("/images/" + filename));
			image.transferTo(file);

			product.setImage(filename);
			productDao.save(product);

			return ResponseEntity.ok("Product updated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while updating product");
		}
	}

	// Endpoint to delete a product
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		try {
			productDao.deleteById(id);
			return ResponseEntity.ok("Product deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting product");
		}
	}

	// Endpoint to search for products
	@GetMapping("/search")
	public ResponseEntity<?> searchProducts(
			@RequestParam("keywords") Optional<String> keywords,
			@RequestParam("p") Optional<Integer> page) {

		String keyword = keywords.orElse(session.get("keywords", ""));
		session.set("keywords", keyword);

		Pageable pageable = PageRequest.of(page.orElse(0), 5);
		Page<Product> productPage = productDao.findAllByNameLike("%" + keyword + "%", pageable);

		return ResponseEntity.ok(productPage);
	}

	// Endpoint to reset (redirect to index)
	@GetMapping("/reset")
	public ResponseEntity<?> reset() {
		return ResponseEntity.ok("Reset successful");
	}
	
}
