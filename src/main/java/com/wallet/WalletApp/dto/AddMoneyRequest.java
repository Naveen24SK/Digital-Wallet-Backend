package com.wallet.WalletApp.dto;

import java.math.BigDecimal;

public class AddMoneyRequest {

    private Long walletId;
    private BigDecimal amount;

    public Long getWalletId() {
        return walletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
