package com.playtomic.tests.wallet.validator.impl;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.service.WalletServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class WalletValidatorTest {

    @InjectMocks
    private WalletValidator walletValidator;

    @Test
    public void validateWalletWithId() {
        try {
            Wallet wallet = new Wallet();
            wallet.setId(1L);
            walletValidator.validateWallet(wallet);
        } catch (WalletServiceException e) {
            fail();
        }
    }

    @Test(expected = WalletServiceException.class)
    public void validateExceptionOfWalletWithoutId() throws WalletServiceException {
        walletValidator.validateWallet(new Wallet());
    }

    @Test
    public void validateMessageExceptionOfWalletWithoutId() {
        try {
            walletValidator.validateWallet(new Wallet());
            fail();
        } catch (WalletServiceException e) {
            assertEquals(WalletValidator.WALLET_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void validateGoodAmount() {
        try {
            BigDecimal amount = BigDecimal.valueOf(100.0);
            BigDecimal balance = BigDecimal.valueOf(200.0);
            walletValidator.validateAmount(amount, balance);
        } catch (WalletServiceException e) {
            fail();
        }
    }

    @Test(expected = WalletServiceException.class)
    public void validateBadAmount() throws WalletServiceException {
        BigDecimal amount = BigDecimal.valueOf(200.0);
        BigDecimal balance = BigDecimal.valueOf(100.0);
        walletValidator.validateAmount(amount, balance);
    }

    @Test
    public void validateMessageExceptionOfBadAmount() {
        try {
            walletValidator.validateWallet(new Wallet());
            fail();
        } catch (WalletServiceException e) {
            assertEquals(WalletValidator.WALLET_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void validateGoodFields() {
        try {
            BigDecimal amount = BigDecimal.valueOf(100.0);
            Movement movement = new Movement();
            movement.setAmount(amount);
            walletValidator.validateRequiredFieldOfMovement(movement);
        } catch (WalletServiceException e) {
            fail();
        }
    }

    @Test(expected = WalletServiceException.class)
    public void validateNullMovement() throws WalletServiceException {
        walletValidator.validateRequiredFieldOfMovement(null);
    }

    @Test
    public void validateMessageExceptionOfNullMovement() {
        try {
            walletValidator.validateRequiredFieldOfMovement(null);
            fail();
        } catch (WalletServiceException e) {
            assertEquals(WalletValidator.TRANSACTION_INFORMATION_IS_MANDATORY, e.getMessage());
        }
    }

    @Test(expected = WalletServiceException.class)
    public void validateMovementWithoutAmount() throws WalletServiceException {
        Movement movement = new Movement();
        walletValidator.validateRequiredFieldOfMovement(movement);
    }

    @Test
    public void validateMessageExceptionOfMovementWithoutAmount() {
        try {
            Movement movement = new Movement();
            walletValidator.validateRequiredFieldOfMovement(movement);
            fail();
        } catch (WalletServiceException e) {
            assertEquals(WalletValidator.AMOUNT_IS_MANDATORY, e.getMessage());
        }
    }
}
