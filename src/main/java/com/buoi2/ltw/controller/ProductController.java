package com.buoi2.ltw.controller;

import com.buoi2.ltw.dto.ProductDTO;
import com.buoi2.ltw.service.ProductService;
import com.buoi2.ltw.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("admin/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private SessionService session;

	// Endpoint to get the product creation page
	@GetMapping("/index")
	public ResponseEntity<?> getCreateProductPage() {
		List<ProductDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	// Endpoint to edit a product
	@GetMapping("/edit/{id}")
	public ResponseEntity<?> getProductById(@PathVariable("id") int id) {
		Optional<ProductDTO> productDTOOpt = productService.getProductById(id);
		if (productDTOOpt.isPresent()) {
			return ResponseEntity.ok(productDTOOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
	}

	@PostMapping("/add")
	public ResponseEntity<?> addProduct(@ModelAttribute ProductDTO productDTO,
										@RequestParam("images") MultipartFile image) {
		try {
			ProductDTO addedProduct = productService.addProduct(productDTO, image);
			return ResponseEntity.ok(Map.of("message", "Product added successfully", "product", addedProduct));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "Error while saving product"));
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateProduct(@RequestParam(value = "id") Integer id,
										   @ModelAttribute ProductDTO productDTO,
										   @RequestParam(value = "images", required = false) MultipartFile image) {
		try {
			ProductDTO updatedProduct = productService.updateProduct(id, productDTO, image);
			if (updatedProduct == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			}
			return ResponseEntity.ok(Map.of("message", "Product updated successfully", "product", updatedProduct));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "Error while updating product"));
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable("id") int id) {
		try {
			productService.deleteProduct(id);
			return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("error", "Error while deleting product"));
		}
	}

	// Endpoint to search for products (no pagination)
	@GetMapping("/search")
	public ResponseEntity<?> searchProducts(@RequestParam("keywords") Optional<String> keywords) {
		String keyword = keywords.orElse(session.get("keywords", ""));
		session.set("keywords", keyword);
		List<ProductDTO> products = productService.searchProductsByName(keyword);
		return ResponseEntity.ok(products);
	}

	// Endpoint to reset (redirect to index)
	@GetMapping("/reset")
	public ResponseEntity<?> reset() {
		return ResponseEntity.ok("Reset successful");
	}
}
