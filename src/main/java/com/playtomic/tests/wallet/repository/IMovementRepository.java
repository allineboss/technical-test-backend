package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.MovementModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface IMovementRepository extends CrudRepository<MovementModel, Long> {

    @Query("SELECT SUM(m.amount) as balance FROM MovementModel m WHERE m.walletModel.id = :walletId")
    BigDecimal getBalanceByWallet(@Param("walletId") Long walletId);
}
