package com.wallet.WalletApp.controller;

import com.wallet.WalletApp.dto.AddMoneyRequest;
import com.wallet.WalletApp.entity.Wallet;
import com.wallet.WalletApp.service.WalletService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "http://localhost:3000")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/add-money")
    public Map<String, Object> addMoney(@RequestBody AddMoneyRequest request) {

        Wallet wallet = walletService.addMoney(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Money added successfully");
        response.put("balance", wallet.getBalance());
        System.out.println("Money added successfully" + wallet.getBalance() + "Rupees");

        return response;
    }
}
