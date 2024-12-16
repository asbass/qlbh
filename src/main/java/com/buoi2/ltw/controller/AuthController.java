package com.buoi2.ltw.controller;

import com.buoi2.ltw.dto.LoginDTO;
import com.buoi2.ltw.dto.RegisterDTO;
import com.buoi2.ltw.entity.Account;
import com.buoi2.ltw.service.AccountService;
import com.buoi2.ltw.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterDTO registerDTO) {
        return accountService.addAccount(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginDTO.getUsername());
            ResponseEntity<Account> acc = accountService.findAccountByUsername(loginDTO.getUsername());
            Account account = acc.getBody();
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("admin", account.isAdmin());
            return ResponseEntity.ok(response);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
