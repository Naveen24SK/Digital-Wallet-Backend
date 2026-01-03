package com.wallet.WalletApp.repository;

import com.wallet.WalletApp.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
        SELECT t FROM Transaction t
        WHERE 
        (t.senderWalletId = :walletId OR t.receiverWalletId = :walletId)
        AND (:type IS NULL OR t.transactionType = :type)
        AND (:status IS NULL OR t.status = :status)
        AND (:category IS NULL OR t.category = :category)
        AND (
            :search IS NULL OR
            t.transactionId LIKE %:search% OR
            t.purpose LIKE %:search%
        )
        AND (:fromDate IS NULL OR t.createdAt >= :fromDate)
        AND (:toDate IS NULL OR t.createdAt <= :toDate)
        ORDER BY t.createdAt DESC
    """)
    Page<Transaction> findWalletTransactions(
            @Param("walletId") Long walletId,
            @Param("type") String type,
            @Param("status") String status,
            @Param("category") String category,
            @Param("search") String search,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );


}
