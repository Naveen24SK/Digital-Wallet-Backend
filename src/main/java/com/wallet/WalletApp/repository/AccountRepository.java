package com.wallet.WalletApp.repository;

import com.wallet.WalletApp.entity.Account;
import com.wallet.WalletApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
    Optional<Account> findByAccountNumber(String accountNumber);
    Optional<Account> findByAccountNumberAndAccountHolderNameIgnoreCase(
            String accountNumber,
            String accountHolderName
    );


}

