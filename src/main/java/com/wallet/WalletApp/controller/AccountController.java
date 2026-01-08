package com.wallet.WalletApp.controller;

import com.wallet.WalletApp.entity.Account;
import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.repository.AccountRepository;
import com.wallet.WalletApp.repository.UserRepository;
import com.wallet.WalletApp.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountController(AccountService accountService,
                             AccountRepository accountRepository,
                             UserRepository userRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    // ---------------------------------------------
    // 1️⃣ GET ACCOUNT BY USER ID (Dashboard Load)
    // ---------------------------------------------
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getAccountByUser(@PathVariable Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return accountRepository.findByUser(user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // ---------------------------------------------
    // 2️⃣ CREATE BANK ACCOUNT
    // ---------------------------------------------
    @PostMapping("/create/{userId}")
    public Account createAccount(@PathVariable Long userId,
                                 @RequestParam String name) {

        return accountService.createAccount(userId, name);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAccount(
            @RequestParam String accountNumber,
            @RequestParam String accountHolder
    ) {
        return accountRepository
                .findByAccountNumberAndAccountHolderNameIgnoreCase(accountNumber, accountHolder)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

