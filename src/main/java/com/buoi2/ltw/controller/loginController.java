package com.buoi2.ltw.controller;

import java.util.Optional;


import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.entity.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller

public class loginController {
	@Autowired
	AccountDAO dao;
	@Autowired
	HttpSession session;

	@RequestMapping("/login")
	public String login() {
		return "admin/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody Account a) {
		Optional<Account> account = dao.checkLogin(a.getUsername(), a.getPassword());

		if (account.isPresent()) {
			session.setAttribute("user", account.get());

			if (account.get().isAdmin()) {
				return ResponseEntity.ok("Admin login successful. Redirect to admin account list.");
			} else {
				return ResponseEntity.ok("User login successful. Redirect to homepage.");
			}
		} else {
			return ResponseEntity.status(401).body("User name or password is wrong");
		}
	}

	@RequestMapping("/logoff")
	public ResponseEntity<String> logoff() {
		session.removeAttribute("user");
		return ResponseEntity.ok("Logged out successfully.");
	}

	@RequestMapping("/register")
	public ResponseEntity<String> register() {
		return ResponseEntity.ok("Register page.");
	}

	@RequestMapping("/activate")
	public ResponseEntity<String> activate() {
		return ResponseEntity.ok("Activation page.");
	}

	@RequestMapping("/forgot-password")
	public ResponseEntity<String> forgot() {
		return ResponseEntity.ok("Forgot password page.");
	}

	@RequestMapping("/chgpwd")
	public ResponseEntity<String> change() {
		return ResponseEntity.ok("Change password page.");
	}
}
