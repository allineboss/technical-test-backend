package com.playtomic.tests.wallet.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class WalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ClientModel clientModel;

    @OneToMany
    private List<MovementModel> movements;
    private Date createDate;

    public WalletModel() {
    }

    public WalletModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<MovementModel> getMovements() {
        return movements;
    }

    public void setMovements(List<MovementModel> movementModel) {
        this.movements = movementModel;
    }
}
