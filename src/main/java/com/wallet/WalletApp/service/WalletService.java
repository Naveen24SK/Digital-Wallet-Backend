package com.wallet.WalletApp.service;

import com.wallet.WalletApp.dto.AddMoneyRequest;
import com.wallet.WalletApp.entity.Transaction;
import com.wallet.WalletApp.entity.Wallet;
import com.wallet.WalletApp.repository.TransactionRepository;
import com.wallet.WalletApp.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;

    public WalletService(WalletRepository walletRepo,
                         TransactionRepository transactionRepo) {
        this.walletRepo = walletRepo;
        this.transactionRepo = transactionRepo;
    }

    public Wallet addMoney(AddMoneyRequest request) {

        // 1️⃣ Fetch wallet
        Wallet wallet = walletRepo.findById(request.getWalletId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        // 2️⃣ Validate amount
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        // 3️⃣ Add balance
        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        walletRepo.save(wallet);

        // 4️⃣ Create transaction log
        Transaction tx = new Transaction();
        tx.setId(UUID.randomUUID().toString());
        tx.setReceiverWalletId(wallet.getId());
        tx.setAmount(request.getAmount());
        tx.setTransactionType("ADD");
        tx.setStatus("SUCCESS");
        tx.setCreatedAt(LocalDateTime.now());

        transactionRepo.save(tx);

        return wallet;
    }
}
