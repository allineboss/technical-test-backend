package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.provider.IWalletProvider;
import com.playtomic.tests.wallet.service.IWalletService;
import com.playtomic.tests.wallet.service.PaymentService;
import com.playtomic.tests.wallet.service.PaymentServiceException;
import com.playtomic.tests.wallet.service.WalletServiceException;
import com.playtomic.tests.wallet.validator.IWalletValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService implements IWalletService {

    protected static final String COULD_NOT_EXECUTE_CHARGE = "Could not execute charge";
    private final IWalletProvider walletProvider;
    private final IWalletValidator walletValidator;
    private final PaymentService paymentService;

    @Autowired
    public WalletService(IWalletProvider walletProvider, IWalletValidator walletValidator, PaymentService paymentService) {
        this.walletProvider = walletProvider;
        this.walletValidator = walletValidator;
        this.paymentService = paymentService;
    }

    @Override
    public Wallet getBalance(Long clientId) {
        return walletProvider.getBalanceByClient(clientId);
    }

    @Override
    public Wallet makePayment(Movement movement) throws WalletServiceException {
        walletValidator.validateRequiredFieldOfMovement(movement);

        Wallet wallet = walletProvider.getById(movement.getWallet().getId());
        walletValidator.validateWallet(wallet);
        walletValidator.validateAmount(movement.getAmount(), wallet.getBalance());

        return walletProvider.makePayment(movement, wallet.getId());
    }

    @Override
    public Wallet returnAmount(Movement movement) throws WalletServiceException {
        walletValidator.validateRequiredFieldOfMovement(movement);

        Wallet wallet = walletProvider.getById(movement.getWallet().getId());
        walletValidator.validateWallet(wallet);

        return walletProvider.returnAmount(movement, wallet.getId());
    }

    @Override
    public Wallet charge(Movement movement) throws WalletServiceException {

        walletValidator.validateRequiredFieldOfMovement(movement);

        Wallet wallet = walletProvider.getById(movement.getWallet().getId());
        walletValidator.validateWallet(wallet);

        try {
            paymentService.charge(movement.getAmount());
        }
        catch (PaymentServiceException e){
            throw new WalletServiceException(COULD_NOT_EXECUTE_CHARGE, e);
        }

        return walletProvider.returnAmount(movement, wallet.getId());
    }
}
