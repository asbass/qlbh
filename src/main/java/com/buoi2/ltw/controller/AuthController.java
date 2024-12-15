package com.buoi2.ltw.controller;

import com.buoi2.ltw.dto.LoginDTO;
import com.buoi2.ltw.dto.RegisterDTO;
import com.buoi2.ltw.entity.Account;
import com.buoi2.ltw.service.AccountService;
import com.buoi2.ltw.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(loginDTO.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
