package com.buoi2.ltw.controller;

import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.entity.Account;
import com.buoi2.ltw.service.SessionService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/account")
public class AccountController {
	@Autowired
	AccountDAO dao;

	@Autowired
	SessionService session;
	@Autowired
	ServletContext app;

	// Tìm kiếm tài khoản
	@GetMapping("/search")
	public ResponseEntity<List<Account>> search(
			@RequestParam(value = "keywords", required = false) String keywords,
			@RequestParam(value = "p", defaultValue = "1") int page) {
		List<Account> accounts = dao.findAll(); // Thêm logic phân trang nếu cần
		return ResponseEntity.ok(accounts);
	}

	// Tạo mới một tài khoản
	@PostMapping("/add")
	public ResponseEntity<?> create(
			@ModelAttribute Account item,
			@RequestParam("images") MultipartFile image) {

		if (image.isEmpty()) {
			return ResponseEntity.badRequest().body("Vui lòng chọn file!");
		}
		try {
			String filename = image.getOriginalFilename();
			String uploadDir = app.getRealPath("/images/");
			File directory = new File(uploadDir);
			if (!directory.exists()) {
				directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
			}
			File file = new File(uploadDir + filename);
			image.transferTo(file);
			item.setPhoto(filename);
			dao.save(item);
			return ResponseEntity.ok("Thêm tài khoản thành công!");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi lưu file!");
		}
	}

	// Cập nhật thông tin tài khoản
	@PutMapping("/update")
	public ResponseEntity<?> update(
			@ModelAttribute Account item,
			@RequestParam("images") MultipartFile image) {
		if (image.isEmpty()) {
			return ResponseEntity.badRequest().body("Vui lòng chọn file!");
		}
		try {
			String filename = image.getOriginalFilename();
			File file = new File(app.getRealPath("/images/" + filename));
			image.transferTo(file);
			item.setPhoto(filename);
			dao.save(item);
			return ResponseEntity.ok("Cập nhật thành công!");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi lưu file!");
		}
	}
	///  xóa tài khoản hiện chưa dx
	// Xóa một tài khoản
	@DeleteMapping("/delete/{username}")
	public ResponseEntity<?> delete(@PathVariable("username") String username) {
		try {
			if (dao.existsById(username)) {
				dao.deleteById(username);
				return ResponseEntity.ok("Xóa tài khoản thành công!");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi xóa tài khoản!");
		}
	}
	/// dang sai can fix
	// Hiển thị thông tin tài khoản
	@GetMapping("/edit/{username}")
	public ResponseEntity<?> edit(@PathVariable("username") String username) {
		Account account = dao.findById(username).orElse(null);
		if (account != null) {
			return ResponseEntity.ok(account);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại!");
		}
	}

	// Reset thông tin tìm kiếm (dummy API chỉ để minh họa)
	@GetMapping("/reset")
	public ResponseEntity<?> reset() {
		return ResponseEntity.ok("Trang đã được reset!");
	}
}
