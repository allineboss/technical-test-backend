package com.playtomic.tests.wallet.dto;

import java.math.BigDecimal;

public class Movement {

    private Wallet wallet;
    private BigDecimal amount;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
