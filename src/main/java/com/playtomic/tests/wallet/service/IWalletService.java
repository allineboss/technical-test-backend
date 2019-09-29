package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;

public interface IWalletService {

    Wallet getBalance(Long clientId);

    Wallet makePayment(Movement movement) throws WalletServiceException;

    Wallet returnAmount(Movement movement) throws WalletServiceException;

    Wallet charge(Movement movement) throws WalletServiceException;
}
