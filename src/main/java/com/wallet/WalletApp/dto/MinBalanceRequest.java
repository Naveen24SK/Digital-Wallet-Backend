package com.wallet.WalletApp.dto;

import java.math.BigDecimal;

public class MinBalanceRequest {

    private BigDecimal minBalance;

    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(BigDecimal minBalance) {
        this.minBalance = minBalance;
    }
}

