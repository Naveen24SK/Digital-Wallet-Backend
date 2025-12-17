package com.wallet.WalletApp.controller;

import com.wallet.WalletApp.dto.LoginRequest;
import com.wallet.WalletApp.dto.RegisterRequest;
import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {



    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}

