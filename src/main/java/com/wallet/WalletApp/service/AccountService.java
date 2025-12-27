package com.wallet.WalletApp.service;

import com.wallet.WalletApp.entity.Account;
import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.repository.AccountRepository;
import com.wallet.WalletApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;

    public AccountService(AccountRepository a, UserRepository u) {
        this.accountRepo = a;
        this.userRepo = u;
    }

    public Account createAccount(Long userId, String holderName) {
        User user = userRepo.findById(userId).orElseThrow();

        Account acc = new Account();
        acc.setUser(user);
        acc.setAccountHolderName(holderName);
        acc.setBalance(new BigDecimal("1000"));
        acc.setStatus("ACTIVE");

// 🔹 FIRST SAVE → DB generates ID
        acc = accountRepo.save(acc);

// 🔹 NOW ID IS AVAILABLE
        Long accountId = acc.getId();   // ✅ SAFE
        String accountNumber = "AC" + (10000 + accountId);

        acc.setAccountNumber(accountNumber);

// 🔹 SECOND SAVE → updates account_number
        return accountRepo.save(acc);

    }
}
