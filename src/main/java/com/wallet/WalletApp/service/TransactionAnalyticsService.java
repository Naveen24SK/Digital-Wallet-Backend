package com.wallet.WalletApp.service;

import com.wallet.WalletApp.dto.DailySpending;
import com.wallet.WalletApp.dto.SpendingCategory;
import com.wallet.WalletApp.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionAnalyticsService {

    private final TransactionRepository txRepo;

    public TransactionAnalyticsService(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    // PIE CHART
    public List<SpendingCategory> getSpendingByCategory(Long walletId, String period) {
        LocalDateTime start = getPeriodStart(period);

        return txRepo.findSpendingByCategory(walletId, start)
                .stream()
                .map(row -> {
                    Object[] r = (Object[]) row;
                    return new SpendingCategory(
                            (String) r[0],
                            (BigDecimal) r[1]
                    );
                })
                .toList();
    }

    // BAR CHART
    public List<DailySpending> getSpendingByPeriod(Long walletId, String period) {
        LocalDateTime start = getPeriodStart(period);

        return txRepo.findDailySpending(walletId, start)
                .stream()
                .map(row -> {
                    Object[] r = (Object[]) row;
                    return new DailySpending(
                            ((java.sql.Date) r[0]).toLocalDate(),
                            (BigDecimal) r[1]
                    );
                })
                .toList();
    }

    private LocalDateTime getPeriodStart(String period) {
        return switch (period.toLowerCase()) {
            case "month" -> LocalDateTime.now().minusMonths(1);
            case "week" -> LocalDateTime.now().minusWeeks(1);
            default -> LocalDateTime.now().minusDays(7);
        };
    }
}
