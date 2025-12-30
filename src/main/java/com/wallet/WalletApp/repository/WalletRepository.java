package com.wallet.WalletApp.repository;


import com.wallet.WalletApp.entity.User;
import com.wallet.WalletApp.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
    List<Wallet> findAllByBalanceLessThan(BigDecimal balance);


}

