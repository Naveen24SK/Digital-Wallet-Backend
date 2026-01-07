package com.wallet.WalletApp.controller;

import com.wallet.WalletApp.entity.Transaction;
import com.wallet.WalletApp.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
//@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final TransactionService txService;

    public TransactionController(TransactionService txService) {
        this.txService = txService;
    }

    // 🔹 WALLET TRANSACTION HISTORY (PAGINATED + FILTERS)
    @GetMapping("/history/{walletId}")
    public Page<Transaction> getWalletHistory(
            @PathVariable Long walletId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,

            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo
    ) {
        return txService.getWalletHistory(
                walletId,
                page,
                size,
                type,
                status,
                category,
                search,
                dateFrom,
                dateTo
        );
    }
}
