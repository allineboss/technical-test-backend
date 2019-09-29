package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.service.IWalletService;
import com.playtomic.tests.wallet.service.WalletServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class WalletControllerTest {

    @InjectMocks
    private WalletController walletController;

    @Mock
    private IWalletService walletService;

    @Test
    public void checkBonus() {
        Long clientId = 1L;

        Wallet expectedWallet = new Wallet(1L, "Client for test", BigDecimal.valueOf(100.0));
        Mockito.when(walletService.getBalance(clientId)).thenReturn(expectedWallet);
        Wallet wallet = walletController.checkBonus(clientId);
        assertEquals(expectedWallet, wallet);
    }

    @Test
    public void makePayment() throws WalletServiceException {
        Movement movement = new Movement();
        Wallet expectedWallet = new Wallet(1L, "Client for test", BigDecimal.valueOf(100.0));
        Mockito.when(walletService.makePayment(movement)).thenReturn(expectedWallet);

        Wallet wallet = walletController.makePayment(movement);
        assertEquals(expectedWallet, wallet);
    }

    @Test
    public void returnAmount() throws WalletServiceException {
        Movement movement = new Movement();
        Wallet expectedWallet = new Wallet(1L, "Client for test", BigDecimal.valueOf(100.0));
        Mockito.when(walletService.returnAmount(movement)).thenReturn(expectedWallet);

        Wallet wallet = walletController.returnAmount(movement);
        assertEquals(expectedWallet, wallet);
    }

    @Test
    public void charge() throws WalletServiceException {
        Movement movement = new Movement();
        Wallet expectedWallet = new Wallet(1L, "Client for test", BigDecimal.valueOf(100.0));
        Mockito.when(walletService.charge(movement)).thenReturn(expectedWallet);

        Wallet wallet = walletController.charge(movement);
        assertEquals(expectedWallet, wallet);
    }
}
