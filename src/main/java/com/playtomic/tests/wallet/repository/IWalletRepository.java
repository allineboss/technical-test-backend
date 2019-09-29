package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.WalletModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWalletRepository extends CrudRepository<WalletModel, Long> {

    WalletModel findById(Long id);

    WalletModel findByClientModel_Id(Long id);
}
