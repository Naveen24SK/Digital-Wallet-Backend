package com.wallet.WalletApp.dto;

import java.math.BigDecimal;

public class SpendingCategory {

    private String category;
    private BigDecimal amount;

    public SpendingCategory(String category, BigDecimal amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
