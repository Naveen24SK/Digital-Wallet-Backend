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
        acc.setAccountNumber("AC" + System.currentTimeMillis());
        acc.setBalance(new BigDecimal("10000")); // mock bank money
        acc.setStatus("ACTIVE");

        return accountRepo.save(acc);
    }
}
