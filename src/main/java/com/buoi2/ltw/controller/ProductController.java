package com.buoi2.ltw.controller;

import java.io.File;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.buoi2.ltw.dao.CategoryDAO;
import com.buoi2.ltw.dao.ProductDAO;
import com.buoi2.ltw.entity.Category;
import com.buoi2.ltw.entity.Product;
import com.buoi2.ltw.service.SessionService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
		List<Product> Products = productDao.findAll();
		return ResponseEntity.ok(Products);

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



	@PostMapping("/add")
	public ResponseEntity<?> addProduct(
			@ModelAttribute Product product,
			@RequestParam("images") Part image) {
		try {
			if (image == null || image.getSize() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No image uploaded.");
			}

			// Xử lý tệp và lưu vào thư mục
			String filename = image.getSubmittedFileName();
			Path path = Path.of("C:/uploadedimages/" + filename);
			Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			// Lưu vào database
			product.setImage(filename);
			productDao.save(product);  // Lưu vào database

			// Trả về thông báo thành công
			return ResponseEntity.ok(new HashMap() {{
				put("message", "Product added successfully");
			}});
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new HashMap<String, String>() {{
						put("message", "Error while saving product");
					}});
		}
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(
			@RequestParam(value = "id") Integer id,  // Nhận ID sản phẩm từ request
			@ModelAttribute Product product,
			@RequestParam(value = "images", required = false) MultipartFile image) {

		// Kiểm tra nếu quality nhỏ hơn 1
		if (product.getQuality() == null || product.getQuality() < 1) {
			return ResponseEntity.badRequest().body(Map.of("message", "Quality must be at least 1"));
		}

		// Kiểm tra xem sản phẩm có tồn tại không
		Optional<Product> existingProductOptional = productDao.findById(id);
		if (!existingProductOptional.isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("message", "Product with ID " + id + " not found"));
		}

		Product existingProduct = existingProductOptional.get();

		// Nếu có hình ảnh mới, xử lý hình ảnh
		if (image != null && !image.isEmpty()) {
			try {
				String filename = image.getOriginalFilename();
				Path path = Paths.get("C:/uploadedimages/" + filename);
				image.transferTo(path);
				existingProduct.setImage(filename);  // Cập nhật ảnh cho sản phẩm
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while uploading image"));
			}
		}

		// Cập nhật các trường còn lại
		existingProduct.setName(product.getName());
		existingProduct.setDescription(product.getDescription());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setQuality(product.getQuality());

		// Tìm và cập nhật Category
		if (product.getCategory() != null && product.getCategory().getId() != null) {
			Optional<Category> categoryOptional = categoryDao.findById(product.getCategory().getId());
			categoryOptional.ifPresent(existingProduct::setCategory);  // Cập nhật Category nếu tồn tại
		}

		try {
			productDao.save(existingProduct);  // Cập nhật sản phẩm
			return ResponseEntity.ok(Map.of("message", "Product updated successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error while updating product"));
		}
	}

	// Endpoint to update a product


	// Endpoint to delete a product
//	@DeleteMapping("/delete/{id}")
//	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
//		try {
//			productDao.deleteById(id);
//			return ResponseEntity.ok("Product deleted successfully");
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while deleting product");
//		}
//	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		try {
			productDao.deleteById(id);
			// Trả về JSON thay vì chuỗi đơn giản
			Map<String, String> response = new HashMap<>();
			response.put("message", "Product deleted successfully");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			Map<String, String> errorResponse = new HashMap<>();
			errorResponse.put("error", "Error while deleting product");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}


	// Endpoint to search for products (no pagination)
	@GetMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestParam("keywords") Optional<String> keywords) {
		String keyword = keywords.orElse(session.get("keywords", ""));
		session.set("keywords", keyword);
		List<Product> products = productDao.findAllByNameLike("%" + keyword + "%");
		return ResponseEntity.ok(products);
	}

	// Endpoint to reset (redirect to index)
	@GetMapping("/reset")
	public ResponseEntity<?> reset() {
		return ResponseEntity.ok("Reset successful");
	}
}
