package com.wallet.WalletApp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DailySpending {
    private LocalDate date;
    private BigDecimal amount;

    public DailySpending(LocalDate date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDate getDate() { return date; }
    public BigDecimal getAmount() { return amount; }
}
