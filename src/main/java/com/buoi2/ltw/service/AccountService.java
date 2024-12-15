package com.buoi2.ltw.service;

import com.buoi2.ltw.dao.AccountDAO;
import com.buoi2.ltw.dto.RegisterDTO;
import com.buoi2.ltw.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private PasswordEncoder encoder;;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> userDetail = accountDAO.findByUsername(username);

        // Converting UserInfo to UserDetails
        return userDetail.map(AccountInforDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    public String addAccount(RegisterDTO registerDTO) {
        Account account = new Account();
        account.setUsername(registerDTO.getUsername());
        account.setPassword(encoder.encode(registerDTO.getPassword()));
        account.setFullname(registerDTO.getFullname());
        account.setEmail(registerDTO.getEmail());
//        account.setPhoto(registerDTO.getPhoto());
        account.setAdmin(registerDTO.getAdmin());
        account.setActivated(registerDTO.getActivated());

        // Lưu đối tượng Account vào database
        accountDAO.save(account);
        return "User Added Successfully";
    }

    public ResponseEntity<List<Account>> listAllAccount() {
        List<Account> listAccount = accountDAO.findAll();
        if (listAccount.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Account>>(listAccount, HttpStatus.OK);
    }

    public ResponseEntity<?> update(
            @ModelAttribute Account item) {
        accountDAO.save(item);
        return ResponseEntity.ok("Cập nhật thành công!");
    }

    public ResponseEntity<?> delete(@PathVariable("username") String username) {
        try {
            if (accountDAO.existsById(username)) {
                accountDAO.deleteById(username);
                return ResponseEntity.ok("Xóa tài khoản thành công!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản không tồn tại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi xóa tài khoản!");
        }
    }
}
