package com.wallet.WalletApp.repository;


import com.wallet.WalletApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findBySenderWalletIdOrReceiverWalletId(Long walletId, Long walletId1);
}

