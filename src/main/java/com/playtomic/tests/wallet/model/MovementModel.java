package com.playtomic.tests.wallet.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class MovementModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    private WalletModel walletModel;

    public MovementModel() {
    }

    public MovementModel(BigDecimal amount, WalletModel wallet) {
        this.amount = amount;
        this.walletModel = wallet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public WalletModel getWalletModel() {
        return walletModel;
    }

    public void setWalletModel(WalletModel walletModel) {
        this.walletModel = walletModel;
    }
}
