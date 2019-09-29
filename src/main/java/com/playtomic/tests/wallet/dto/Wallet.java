package com.playtomic.tests.wallet.dto;

import java.math.BigDecimal;

public class Wallet {

    private Long id;
    private String clientName;
    private BigDecimal balance;

    public Wallet() {
    }

    public Wallet(Long id, String clientName, BigDecimal balance) {
        this.id = id;
        this.clientName = clientName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
