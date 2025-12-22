package com.wallet.WalletApp.service;

import com.wallet.WalletApp.dto.AddMoneyRequest;
import com.wallet.WalletApp.entity.Account;
import com.wallet.WalletApp.entity.Transaction;
import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Wallet;
import com.wallet.WalletApp.repository.AccountRepository;
import com.wallet.WalletApp.repository.TransactionRepository;
import com.wallet.WalletApp.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepo;
    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public WalletService(WalletRepository w, AccountRepository a, TransactionRepository t) {
        this.walletRepo = w;
        this.accountRepo = a;
        this.txRepo = t;
    }

    public Wallet createWallet(User user) {
        Wallet w = new Wallet();
        w.setUser(user);
        w.setBalance(BigDecimal.ZERO);
        return walletRepo.save(w);
    }

    @Transactional
    public void addMoney(Long accountId, Long walletId, BigDecimal amount) {
        Account acc = accountRepo.findById(accountId).orElseThrow();
        Wallet wallet = walletRepo.findById(walletId).orElseThrow();

        acc.setBalance(acc.getBalance().subtract(amount));
        wallet.setBalance(wallet.getBalance().add(amount));

        accountRepo.save(acc);
        walletRepo.save(wallet);

        Transaction tx = new Transaction();
        tx.setId(UUID.randomUUID().toString());
        tx.setSenderAccountId(acc.getId());
        tx.setReceiverWalletId(wallet.getId());
        tx.setAmount(amount);
        tx.setTransactionType("ADD");
        tx.setStatus("SUCCESS");

        txRepo.save(tx);
    }

    public List<Transaction> history(Long walletId) {
        return txRepo.findBySenderWalletIdOrReceiverWalletId(walletId, walletId);
    }
}
