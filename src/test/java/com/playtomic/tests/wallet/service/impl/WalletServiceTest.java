package com.playtomic.tests.wallet.service.impl;

import com.playtomic.tests.wallet.dto.Movement;
import com.playtomic.tests.wallet.dto.Wallet;
import com.playtomic.tests.wallet.provider.IWalletProvider;
import com.playtomic.tests.wallet.service.PaymentService;
import com.playtomic.tests.wallet.service.PaymentServiceException;
import com.playtomic.tests.wallet.service.WalletServiceException;
import com.playtomic.tests.wallet.validator.IWalletValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceTest {

    @InjectMocks
    private WalletService walletService;

    @Mock
    private IWalletProvider walletProvider;

    @Mock
    private IWalletValidator walletValidator;

    @Mock
    private PaymentService paymentService;

    @Test
    public void getBalanceForClient(){
        long clientId = 1L;
        Wallet expectedWallet = new Wallet(1L, "Client name", BigDecimal.valueOf(200.0));
        Mockito.when(walletProvider.getBalanceByClient(clientId)).thenReturn(expectedWallet);

        Wallet wallet = walletService.getBalance(clientId);
        Mockito.verify(walletProvider, Mockito.times(1)).getBalanceByClient(clientId);
        assertEquals(expectedWallet, wallet);
    }

    @Test
    public void makePayment() throws WalletServiceException {
        long walletId = 1L;
        Wallet savedWallet = new Wallet(walletId, "Client name", BigDecimal.valueOf(200.0));
        Mockito.when(walletProvider.getById(walletId)).thenReturn(savedWallet);

        Wallet walletForMovement = new Wallet();
        walletForMovement.setId(walletId);

        Movement movement = new Movement();
        movement.setWallet(walletForMovement);
        movement.setAmount(BigDecimal.valueOf(200.0));

        Wallet expectedWallet = new Wallet(walletId, "Client name", BigDecimal.valueOf(0.0));
        Mockito.when(walletProvider.makePayment(movement, walletForMovement.getId())).thenReturn(expectedWallet);

        Wallet wallet = walletService.makePayment(movement);
        Mockito.verify(walletValidator, Mockito.times(1)).validateRequiredFieldOfMovement(movement);
        Mockito.verify(walletValidator, Mockito.times(1)).validateWallet(savedWallet);
        Mockito.verify(walletValidator, Mockito.times(1)).validateAmount(movement.getAmount(), savedWallet.getBalance());
        Mockito.verify(walletProvider, Mockito.times(1)).makePayment(movement, walletForMovement.getId());
        assertEquals(expectedWallet, wallet);
    }

    @Test
    public void returnAmount() throws WalletServiceException {
        long walletId = 1L;
        Wallet savedWallet = new Wallet(walletId, "Client name", BigDecimal.valueOf(200.0));
        Mockito.when(walletProvider.getById(walletId)).thenReturn(savedWallet);

        Wallet walletForMovement = new Wallet();
        walletForMovement.setId(walletId);

        Movement movement = new Movement();
        movement.setWallet(walletForMovement);
        movement.setAmount(BigDecimal.valueOf(200.0));

        Wallet expectedWallet = new Wallet(walletId, "Client name", BigDecimal.valueOf(400.0));
        Mockito.when(walletProvider.returnAmount(movement, walletForMovement.getId())).thenReturn(expectedWallet);

        Wallet wallet = walletService.returnAmount(movement);
        Mockito.verify(walletValidator, Mockito.times(1)).validateRequiredFieldOfMovement(movement);
        Mockito.verify(walletValidator, Mockito.times(1)).validateWallet(savedWallet);
        Mockito.verify(walletProvider, Mockito.times(1)).returnAmount(movement, walletForMovement.getId());
        Mockito.verify(walletValidator, Mockito.times(0))
                .validateAmount(Mockito.any(BigDecimal.class), Mockito.any(BigDecimal.class));
        assertEquals(expectedWallet, wallet);
    }

    @Test
    public void chargeWithGoodValue() throws WalletServiceException {
        long walletId = 1L;
        Wallet savedWallet = new Wallet(walletId, "Client name", BigDecimal.valueOf(200.0));
        Mockito.when(walletProvider.getById(walletId)).thenReturn(savedWallet);

        Wallet walletForMovement = new Wallet();
        walletForMovement.setId(walletId);

        Movement movement = new Movement();
        movement.setWallet(walletForMovement);
        movement.setAmount(BigDecimal.valueOf(200.0));

        Wallet expectedWallet = new Wallet(walletId, "Client name", BigDecimal.valueOf(400.0));
        Mockito.when(walletProvider.returnAmount(movement, walletForMovement.getId())).thenReturn(expectedWallet);

        Wallet wallet = walletService.charge(movement);
        Mockito.verify(walletValidator, Mockito.times(1)).validateRequiredFieldOfMovement(movement);
        Mockito.verify(walletValidator, Mockito.times(1)).validateWallet(savedWallet);
        Mockito.verify(walletProvider, Mockito.times(1)).returnAmount(movement, walletForMovement.getId());
        Mockito.verify(walletValidator, Mockito.times(0))
                .validateAmount(Mockito.any(BigDecimal.class), Mockito.any(BigDecimal.class));
        assertEquals(expectedWallet, wallet);
    }

    @Test
    public void chargeWithBadValue() throws WalletServiceException, PaymentServiceException {
        long walletId = 1L;
        Wallet savedWallet = new Wallet(walletId, "Client name", BigDecimal.valueOf(200.0));
        Mockito.when(walletProvider.getById(walletId)).thenReturn(savedWallet);

        Wallet walletForMovement = new Wallet();
        walletForMovement.setId(walletId);

        Movement movement = new Movement();
        movement.setWallet(walletForMovement);
        BigDecimal amount = BigDecimal.valueOf(1.0);
        movement.setAmount(amount);

        Mockito.doThrow(PaymentServiceException.class).when(paymentService).charge(Mockito.any(BigDecimal.class));

        try {
            walletService.charge(movement);
            fail();
        } catch (WalletServiceException e){
            assertEquals(WalletService.COULD_NOT_EXECUTE_CHARGE, e.getMessage());
        }

        Mockito.verify(walletValidator, Mockito.times(1)).validateRequiredFieldOfMovement(movement);
        Mockito.verify(walletValidator, Mockito.times(1)).validateWallet(savedWallet);
        Mockito.verify(walletProvider, Mockito.times(0)).returnAmount(movement, walletForMovement.getId());
        Mockito.verify(walletValidator, Mockito.times(0))
                .validateAmount(Mockito.any(BigDecimal.class), Mockito.any(BigDecimal.class));
    }
}
