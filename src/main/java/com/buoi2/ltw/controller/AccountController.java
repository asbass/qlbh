package com.buoi2.ltw.controller;

import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.entity.Account;
import com.buoi2.ltw.service.AccountService;
import com.buoi2.ltw.service.SessionService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin/account")
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping()
	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<Account>> getList() {
		return accountService.listAllAccount();
	}

	// Cập nhật thông tin tài khoản
	@PutMapping("/update")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> update(
			@ModelAttribute Account item) {
		return accountService.update(item);
	}

	// Xóa một tài khoản
	@DeleteMapping("/delete/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("username") String username) {
		return accountService.delete(username);
	}

}
