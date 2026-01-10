package com.wallet.WalletApp.service;

import com.wallet.WalletApp.dto.LoginRequest;
import com.wallet.WalletApp.dto.RegisterRequest;
import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Wallet;
import com.wallet.WalletApp.repository.UserRepository;
import com.wallet.WalletApp.repository.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final WalletRepository walletRepo;

    public AuthService(UserRepository userRepo, WalletRepository walletRepo) {
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
    }

    // REGISTER
    public void register(RegisterRequest req) {

        if (userRepo.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        user.setAlertSent(false); // ✅ IMPORTANT

        userRepo.save(user);

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        walletRepo.save(wallet);
    }



    // LOGIN
    public User login(LoginRequest req) {

        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "User not found"
                        )
                );

        if (!req.getPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid password"
            );
        }

        return user;
    }

}
