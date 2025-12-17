package com.wallet.WalletApp.repository;


import com.wallet.WalletApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}

