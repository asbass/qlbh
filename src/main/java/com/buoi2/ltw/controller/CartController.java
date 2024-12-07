package com.buoi2.ltw.controller;

import com.buoi2.ltw.dao.OrderDAO;
import com.buoi2.ltw.dao.ProductDAO;
import com.buoi2.ltw.entity.Product;
import com.buoi2.ltw.utils.CartItems;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

// cart chưa xong
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ProductDAO pd;

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private HttpSession ss;

	// Lấy thông tin giỏ hàng
	@GetMapping("/view")
	public ResponseEntity<?> viewCart() {
		Object cartItems = ss.getAttribute("cart");
		if (cartItems == null) {
			return ResponseEntity.status(400).body("Cart is empty or not initialized.");
		}
		Object user = ss.getAttribute("user");
		return ResponseEntity.ok().body(Map.of("cartItems", cartItems, "user", user));
	}

	// Thêm sản phẩm vào giỏ hàng
	@PostMapping("/add/{id}")
	public ResponseEntity<?> addToCart(@PathVariable("id") int id,
									   @RequestParam("quantity") int quantity) {
		Optional<Product> optionalProduct = pd.findById(id);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			CartItems cart = (CartItems) ss.getAttribute("cart");
			cart.addItems(product, quantity);
			return ResponseEntity.ok("Product added to cart successfully.");
		} else {
			return ResponseEntity.status(404).body("Product not found.");
		}
	}

	// Thay đổi số lượng sản phẩm trong giỏ hàng
	@PutMapping("/modify/{id}")
	public ResponseEntity<?> modifyCartItem(@PathVariable("id") int id,
											@RequestParam("quantity") int quantity) {
		Optional<Product> optionalProduct = pd.findById(id);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			CartItems cart = (CartItems) ss.getAttribute("cart");
			cart.modifyQuantity(product, quantity);
			return ResponseEntity.ok("Product quantity updated successfully.");
		} else {
			return ResponseEntity.status(404).body("Product not found.");
		}
	}

	// Xóa sản phẩm khỏi giỏ hàng
	@DeleteMapping("/remove/{id}")
	public ResponseEntity<?> removeCartItem(@PathVariable("id") String id) {
		CartItems cart = (CartItems) ss.getAttribute("cart");
		cart.removeItem(id);
		return ResponseEntity.ok("Product removed from cart successfully.");
	}
	
}
