package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.model.ClientModel;
import com.playtomic.tests.wallet.model.WalletModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class IWalletRepositoryTest {

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IWalletRepository walletRepository;

    @Test
    public void testSaveNewWallet(){
        ClientModel clientModel = new ClientModel("Client for test", "client.for.test@test.com");
        clientRepository.save(clientModel);

        WalletModel walletModel = new WalletModel(clientModel);
        walletRepository.save(walletModel);
        WalletModel savedWallet = walletRepository.findOne(walletModel.getId());
        assertEquals(walletModel.getClientModel().getName(), savedWallet.getClientModel().getName());
    }

    @Test
    public void findById(){
        ClientModel clientModel = new ClientModel("Client for test", "client.for.test@test.com");
        clientRepository.save(clientModel);

        WalletModel walletModel = new WalletModel(clientModel);
        walletRepository.save(walletModel);

        WalletModel savedWallet = walletRepository.findById(walletModel.getId());
        assertEquals(walletModel.getClientModel().getName(), savedWallet.getClientModel().getName());
    }

    @Test
    public void findByClientId(){
        ClientModel clientModel = new ClientModel("Client for test", "client.for.test@test.com");
        clientRepository.save(clientModel);

        WalletModel walletModel = new WalletModel(clientModel);
        walletRepository.save(walletModel);

        WalletModel savedWallet = walletRepository.findByClientModel_Id(clientModel.getId());
        ClientModel clientFromSavedWallet = savedWallet.getClientModel();
        assertEquals(walletModel.getClientModel().getName(), clientFromSavedWallet.getName());
    }
}
