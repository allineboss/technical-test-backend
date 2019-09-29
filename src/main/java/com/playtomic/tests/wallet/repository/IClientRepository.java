package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.ClientModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends CrudRepository<ClientModel, Long> {

}
