package com.wallet.WalletApp.repository;

import com.wallet.WalletApp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderWalletIdOrReceiverWalletId(Long senderId, Long receiverId);

    // 🔹 CATEGORY WISE SPENDING (PIE)
    @Query("""
        SELECT t.category, SUM(t.amount)
        FROM Transaction t
        WHERE t.senderWalletId = :walletId
          AND t.transactionType = 'SEND_MONEY'
          AND t.createdAt >= :start
        GROUP BY t.category
    """)
    List<Object[]> findSpendingByCategory(Long walletId, LocalDateTime start);

    // 🔹 DAILY SPENDING (BAR)
    @Query("""
        SELECT DATE(t.createdAt), SUM(t.amount)
        FROM Transaction t
        WHERE t.senderWalletId = :walletId
          AND t.transactionType = 'SEND_MONEY'
          AND t.createdAt >= :start
        GROUP BY DATE(t.createdAt)
        ORDER BY DATE(t.createdAt)
    """)
    List<Object[]> findDailySpending(Long walletId, LocalDateTime start);
}
