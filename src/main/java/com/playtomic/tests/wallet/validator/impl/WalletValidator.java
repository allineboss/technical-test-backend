package com.playtomic.tests.wallet.validator.impl;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.service.WalletServiceException;
import com.playtomic.tests.wallet.validator.IWalletValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletValidator implements IWalletValidator {

    protected static final String TRANSACTION_INFORMATION_IS_MANDATORY = "The transaction insformation is mandatory";
    protected static final String AMOUNT_IS_MANDATORY = "The field amount is mandatory";
    protected static final String AMOUNT_NOT_AVAILABLE = "Amount not available for transaction";
    protected static final String WALLET_NOT_FOUND = "Wallet not found";

    @Override
    public void validateWallet(Wallet wallet) throws WalletServiceException {
        if (wallet.getId() == null) {
            throw new WalletServiceException(WALLET_NOT_FOUND);
        }
    }

    @Override
    public void validateAmount(BigDecimal amount, BigDecimal balance) throws WalletServiceException {
        if (amount.compareTo(balance) > 0) {
            throw new WalletServiceException(AMOUNT_NOT_AVAILABLE);
        }
    }

    @Override
    public void validateRequiredFieldOfMovement(Movement movement) throws WalletServiceException {
        if (movement == null) {
            throw new WalletServiceException(TRANSACTION_INFORMATION_IS_MANDATORY);
        }
        if (movement.getAmount() == null) {
            throw new WalletServiceException(AMOUNT_IS_MANDATORY);
        }
    }
}
