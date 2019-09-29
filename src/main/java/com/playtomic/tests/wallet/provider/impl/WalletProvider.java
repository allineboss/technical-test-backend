package com.playtomic.tests.wallet.provider.impl;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.model.MovementModel;
import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.provider.IWalletProvider;
import com.playtomic.tests.wallet.repository.IMovementRepository;
import com.playtomic.tests.wallet.repository.IWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletProvider implements IWalletProvider {

    private final IWalletRepository walletRepository;

    private final IMovementRepository movementRepository;

    @Autowired
    public WalletProvider(IWalletRepository walletRepository, IMovementRepository movementRepository) {
        this.walletRepository = walletRepository;
        this.movementRepository = movementRepository;
    }

    @Override
    public Wallet getBalanceByClient(Long clientId) {
        WalletModel walletModel = walletRepository.findByClientModel_Id(clientId);
        return getWallet(walletModel);
    }

    @Override
    public Wallet getById(Long id) {
        if (id == null) {
            return new Wallet();
        }
        WalletModel walletModel = walletRepository.findById(id);
        return getWallet(walletModel);
    }

    @Override
    public Wallet makePayment(Movement movement, Long walletId) {
        if (walletId == null) {
            return new Wallet();
        }

        WalletModel walletModel = walletRepository.findById(walletId);
        if (walletModel == null) {
            return new Wallet();
        }

        MovementModel movementModel = new MovementModel(movement.getAmount().negate(), walletModel);
        movementRepository.save(movementModel);
        return getWallet(walletModel);
    }

    @Override
    public Wallet returnAmount(Movement movement, Long walletId) {
        if (walletId == null) {
            return new Wallet();
        }

        WalletModel walletModel = walletRepository.findById(walletId);
        if (walletModel == null) {
            return new Wallet();
        }

        MovementModel movementModel = new MovementModel(movement.getAmount(), walletModel);
        movementRepository.save(movementModel);
        return getWallet(walletModel);
    }

    private Wallet getWallet(WalletModel walletModel) {
        if (walletModel != null) {
            BigDecimal balance = movementRepository.getBalanceByWallet(walletModel.getId());
            return new Wallet(walletModel.getId(), walletModel.getClientModel().getName(), balance);
        }

        return new Wallet();
    }
}
