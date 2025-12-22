package com.wallet.WalletApp.controller;

import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Transaction;
import com.wallet.WalletApp.entity.Wallet;
import com.wallet.WalletApp.repository.UserRepository;
import com.wallet.WalletApp.repository.WalletRepository;
import com.wallet.WalletApp.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "http://localhost:5173")

public class WalletController {

    private final WalletService walletService;
    private final WalletRepository walletRepo;
    private final UserRepository userRepo;

    public WalletController(WalletService walletService,
                            WalletRepository walletRepo,
                            UserRepository userRepo) {
        this.walletService = walletService;
        this.walletRepo = walletRepo;
        this.userRepo = userRepo;
    }

    // CREATE WALLET
    @PostMapping("/create/{userId}")
    public Wallet createWallet(@PathVariable Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return walletService.createWallet(user);
    }

    // ADD MONEY FROM ACCOUNT → WALLET
    @PostMapping("/add")
    public String addMoney(@RequestParam Long accountId,
                           @RequestParam Long walletId,
                           @RequestParam BigDecimal amount) {
        walletService.addMoney(accountId, walletId, amount);
        return "Money added successfully";
    }

    // TRANSACTION HISTORY
    @GetMapping("/history/{walletId}")
    public List<Transaction> history(@PathVariable Long walletId) {
        return walletService.history(walletId);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getWalletByUser(@PathVariable Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return walletRepo.findByUser(user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
