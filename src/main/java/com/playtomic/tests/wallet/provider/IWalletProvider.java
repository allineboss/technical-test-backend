package com.playtomic.tests.wallet.provider;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;

public interface IWalletProvider {

    Wallet getBalanceByClient(Long clientId);

    Wallet getById(Long id);

    Wallet makePayment(Movement movement, Long walletId);

    Wallet returnAmount(Movement movement, Long walletId);
}
