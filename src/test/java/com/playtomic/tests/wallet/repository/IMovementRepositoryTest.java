package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.ClientModel;
import com.playtomic.tests.wallet.model.MovementModel;
import com.playtomic.tests.wallet.model.WalletModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class IMovementRepositoryTest {

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IWalletRepository walletRepository;

    @Autowired
    private IMovementRepository movementRepository;

    @Test
    public void testSaveNewMovement(){
        ClientModel clientModel = new ClientModel("Client for test", "client.for.test@test.com");
        clientRepository.save(clientModel);

        WalletModel walletModel = new WalletModel(clientModel);
        walletRepository.save(walletModel);

        BigDecimal amount = BigDecimal.valueOf(100.0);
        MovementModel movementModel = new MovementModel(amount, walletModel);
        movementRepository.save(movementModel);
        MovementModel savedMovement = movementRepository.findOne(movementModel.getId());
        assertEquals(amount.setScale(2, RoundingMode.HALF_UP), savedMovement.getAmount());
    }

    @Test
    public void getBalanceByWallet(){
        ClientModel clientModel = new ClientModel("Client for test", "client.for.test@test.com");
        clientRepository.save(clientModel);

        WalletModel walletModel = new WalletModel(clientModel);
        walletRepository.save(walletModel);

        BigDecimal amount = BigDecimal.valueOf(100.0);
        MovementModel movementModel = new MovementModel(amount, walletModel);
        movementRepository.save(movementModel);

        BigDecimal balance = movementRepository.getBalanceByWallet(walletModel.getId());
        assertEquals(amount.setScale(2, RoundingMode.HALF_UP), balance);
    }

    @Test
    public void getBalanceByWalletWithMultipleMovements(){
        ClientModel clientModel = new ClientModel("Client for test", "client.for.test@test.com");
        clientRepository.save(clientModel);

        WalletModel walletModel = new WalletModel(clientModel);
        walletRepository.save(walletModel);

        MovementModel movementModel1 = new MovementModel(BigDecimal.valueOf(100.0), walletModel);
        movementRepository.save(movementModel1);

        MovementModel movementModel2 = new MovementModel(BigDecimal.valueOf(10.0).negate(), walletModel);
        movementRepository.save(movementModel2);

        MovementModel movementModel3 = new MovementModel(BigDecimal.valueOf(50.0), walletModel);
        movementRepository.save(movementModel3);

        BigDecimal balance = movementRepository.getBalanceByWallet(walletModel.getId());
        assertEquals(BigDecimal.valueOf(140.0).setScale(2, RoundingMode.HALF_UP), balance);
    }
}
