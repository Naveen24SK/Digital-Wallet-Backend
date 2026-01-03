package com.wallet.WalletApp.service;

import com.wallet.WalletApp.entity.Transaction;
import com.wallet.WalletApp.repository.TransactionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository txRepo;

    public TransactionService(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    public Page<Transaction> getWalletHistory(
            Long walletId,
            int page,
            int size,
            String type,
            String status,
            String category,
            String search,
            String dateFrom,
            String dateTo
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        LocalDateTime from = null;
        LocalDateTime to = null;

        if (dateFrom != null && !dateFrom.isBlank()) {
            from = LocalDate.parse(dateFrom).atStartOfDay();
        }

        if (dateTo != null && !dateTo.isBlank()) {
            to = LocalDate.parse(dateTo).atTime(23, 59, 59);
        }

        return txRepo.findWalletTransactions(
                walletId,
                emptyToNull(type),
                emptyToNull(status),
                emptyToNull(category),
                emptyToNull(search),
                from,
                to,
                pageable
        );
    }

    private String emptyToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }
}
