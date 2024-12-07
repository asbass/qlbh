package com.buoi2.ltw.controller;

import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
public class RegistratorController {
	@Autowired
	private AccountDAO accountDao;

	// Endpoint to display registration form
	@GetMapping("/registrator")
	public ResponseEntity<String> getRegistrationForm() {
		return ResponseEntity.ok("Registration page displayed");
	}

	// Endpoint to handle account registration
	@PostMapping("/registrator")
	public ResponseEntity<?> registerAccount(@RequestBody Account account) {
		try {
			// Set default photo
			account.setPhoto("user.png");
			accountDao.save(account);

			return ResponseEntity.ok("Account registered successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during account registration");
		}
	}
}
