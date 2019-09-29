package com.playtomic.tests.wallet.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class ClientModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String mail;

    @OneToMany
    private List<WalletModel> walletModel;

    private Date createDate;

    public ClientModel() {
    }

    public ClientModel(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<WalletModel> getWalletModel() {
        return walletModel;
    }

    public void setWalletModel(List<WalletModel> walletModel) {
        this.walletModel = walletModel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
