package com.playtomic.tests.wallet.validator;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.service.WalletServiceException;

import java.math.BigDecimal;

public interface IWalletValidator {

    void validateRequiredFieldOfMovement(Movement movement) throws WalletServiceException;

    void validateWallet(Wallet wallet) throws WalletServiceException;

    void validateAmount(BigDecimal amount, BigDecimal balance) throws WalletServiceException;
}
